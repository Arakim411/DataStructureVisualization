package com.arakim.datastructurevisualization.ui.genericPicker.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.ColorView
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.Dimens
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.helpers.DropDownBox
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.helpers.PickDataTypeDialog
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.ColorType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.DurationType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.NumericType
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.StringWrapper
import com.arakim.datastructurevisualization.ui.util.getString
import com.arakim.datastructurevisualization.ui.util.mapToImmutable

@Composable
fun GenericPickerDetailsDialog(
    items: ImmutableList<GenericPickerItem<*>>,
    onDismissRequest: () -> Unit,
    onAction: (NewDataPickedAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    val groupToItem = remember(items) {
        items.groupBy { it.group }.mapValues { it.value.mapToImmutable { item -> item } }
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .width(270.dp)
                .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.medium)
                .padding(horizontal = Dimens.PickerContainerPadding)
                .verticalScroll(scrollState)

        ) {

            groupToItem.forEach { (group, items) ->
                GroupView(
                    modifier = Modifier
                        .padding(vertical = 16.dp),
                    group = group,
                    items = items,
                    onAction = onAction,
                )
            }
        }
    }
}


@Composable
private fun GroupView(
    modifier: Modifier,
    group: StringWrapper,
    items: ImmutableList<GenericPickerItem<*>>,
    onAction: (NewDataPickedAction) -> Unit
) {
    Column(modifier = modifier) {

        GroupTitle(group.getString())
        Spacer(modifier = Modifier.height(8.dp))

        items.forEach { item ->
            PickerItemView(
                modifier = Modifier.fillMaxSize(),
                item = item,
                onAction = onAction,
            )
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}

@Composable
private fun ColumnScope.GroupTitle(title: String) {
    Text(
        modifier = Modifier.align(CenterHorizontally),
        text = title,
        style = MaterialTheme.typography.titleMedium,
    )
}

@Composable
private fun PickerItemView(
    modifier: Modifier,
    item: GenericPickerItem<*>,
    onAction: (NewDataPickedAction) -> Unit
) {
    var currentlyPickingItem: GenericPickerItem<*>? by remember { mutableStateOf(null) }

    currentlyPickingItem?.also {
        PickDataTypeDialog(
            item = it,
            onAction = onAction,
            onDismissRequest = { currentlyPickingItem = null },
        )
    }


    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(painter = painterResource(id = item.iconResId), contentDescription = null)

        Text(
            text = item.title.getString(),
            style = MaterialTheme.typography.bodyMedium,
        )
        DataTypeView(
            modifier = Modifier.clickable {
                currentlyPickingItem = item
            },
            item = item,
        )
    }
}


@Composable
private fun RowScope.DataTypeView(
    modifier: Modifier,
    item: GenericPickerItem<*>,
) {

    when (val dataType = item.pickingDataType) {
        is ColorType -> ColorView(
            modifier = modifier,
            color = dataType.value,
        )

        is DurationType -> DropDownBox(
            modifier = modifier,
            text = dataType.value.toString(),
        )

        is NumericType -> DropDownBox(
            modifier = modifier,
            text = dataType.value.toString().plus(" ${dataType.unit}"),
        )
    }
}
