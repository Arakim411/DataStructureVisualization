package com.arakim.datastructurevisualization.ui.genericPicker.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arakim.datastructurevisualization.ui.common.genericpicker.R
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.ColorView
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.helpers.PickDataTypeDialog
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.ColorType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.DurationType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.NumericType
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Composable
internal fun FloatingModalItemsView(
    items: ImmutableList<GenericPickerItem<*>>,
    onAction: (NewDataPickedAction) -> Unit,
    onMoreDetailsClick: () -> Unit,
) {
    val isExpanded = remember { mutableStateOf(true) }

    var currentlyPickingItem: GenericPickerItem<*>? by remember { mutableStateOf(null) }

    currentlyPickingItem?.also {
        PickDataTypeDialog(
            item = it,
            onAction = onAction,
            onDismissRequest = { currentlyPickingItem = null },
        )
    }

    Column(
        modifier = Modifier
            .width(100.dp)
            .background(MaterialTheme.colorScheme.inverseOnSurface, MaterialTheme.shapes.medium)
            .padding(8.dp)
    ) {

        SettingsView(
            modifier = Modifier.fillMaxWidth(),
            onMoreDetailsClick = onMoreDetailsClick,
            onChangeSizeClicked = { isExpanded.value = !isExpanded.value },
            isExpanded = isExpanded.value
        )

        AnimatedVisibility(visible = isExpanded.value) {
            Column {
                items.forEach {

                    ModalItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                currentlyPickingItem = it
                            },
                        item = it
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun SettingsView(
    modifier: Modifier,
    onMoreDetailsClick: () -> Unit,
    onChangeSizeClicked: () -> Unit,
    isExpanded: Boolean,
) {
    val icon = if (isExpanded) R.drawable.baseline_expand_less_24 else R.drawable.baseline_expand_more_24
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.clickable { onChangeSizeClicked() },
            painter = painterResource(id = icon),
            contentDescription = null,
        )

        Icon(
            modifier = Modifier.clickable { onMoreDetailsClick() },
            painter = painterResource(id = R.drawable.outline_more_horiz_24),
            contentDescription = null,
        )
    }
}

@Composable
private fun ModalItem(
    modifier: Modifier,
    item: GenericPickerItem<*>,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(painter = painterResource(id = item.iconResId), contentDescription = null)
        when (val dataType = item.pickingDataType) {
            is ColorType -> ColorView(color = dataType.value)

            is DurationType -> Text(
                text = dataType.value.toString(),
                fontSize = 13.sp
            )

            is NumericType -> Text(
                text = dataType.value.toString().plus(" ${dataType.unit}"),
                fontSize = 13.sp
            )
        }
    }
}