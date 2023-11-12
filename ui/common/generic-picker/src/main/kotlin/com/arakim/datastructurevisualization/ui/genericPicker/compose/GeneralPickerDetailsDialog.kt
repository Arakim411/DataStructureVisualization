package com.arakim.datastructurevisualization.ui.genericPicker.compose

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.ColorPickerView
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.Dimens
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.DurationPickerView
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.NumericPickerView
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction.ColorPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction.DurationPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction.NumberPickedAction
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
                .width(250.dp)
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
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = item.title.getString(),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.width(8.dp))
        DataTypeView(item, onAction)
    }
}


@Composable
private fun RowScope.DataTypeView(
    item: GenericPickerItem<*>,
    onAction: (NewDataPickedAction) -> Unit,
) {

    when (val dataType = item.pickingDataType) {
        is ColorType -> ColorPickerView(
            type = dataType,
            onNewColorPicked = { color ->
                onAction(ColorPickedAction(item.id, color))
            }
        )

        is DurationType -> DurationPickerView(
            durationType = dataType,
            onNewDurationPicked = { duration ->
                onAction(DurationPickedAction(item.id, duration))
            }
        )

        is NumericType -> NumericPickerView(
            number = dataType.value,
            unit = dataType.unit,
            title = item.title.getString(),
            onNewNumberPicked = { newNumber ->
                onAction(NumberPickedAction(item.id, newNumber))
            }
        )
    }
}
