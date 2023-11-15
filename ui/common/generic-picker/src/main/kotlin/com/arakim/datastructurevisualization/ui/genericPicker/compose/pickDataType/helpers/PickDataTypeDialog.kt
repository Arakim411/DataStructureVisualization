package com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.helpers

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.arakim.datastructurevisualization.ui.common.genericpicker.R.string
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.PickColorView
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.PickDurationView
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.PickNumberView
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction.ColorPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction.DurationPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction.NumberPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.ColorType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.DurationType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.NumericType
import com.arakim.datastructurevisualization.ui.util.getString

//TODO stick m3 rules
@Composable
fun PickDataTypeDialog(
    item: GenericPickerItem<*>,
    onAction: (NewDataPickedAction) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {

        when (val dataType = item.pickingDataType) {
            is ColorType -> PickColorView(
                color = dataType.value,
                onNewColorPicked = { color ->
                    onAction(ColorPickedAction(item.id, color))
                },
                title = stringResource(string.pick_color_title,item.title.getString()),
                onCancel = onDismissRequest,
            )

            is DurationType -> PickDurationView(
                durationType = dataType,
                title = item.title.getString(),
                onDurationPicked = { duration ->
                    onAction(DurationPickedAction(item.id, duration))
                },
                onCancel = onDismissRequest,
            )

            is NumericType ->
                PickNumberView(
                    currentNumber = dataType.value,
                    unit = dataType.unit,
                    onNumberPicked = { number ->
                        onAction(NumberPickedAction(item.id, number))
                    },
                    title = item.title.getString(),
                    onCancel = onDismissRequest,
                )
        }
    }
}