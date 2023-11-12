package com.arakim.datastructurevisualization.ui.genericPicker.presenter.reducer

import androidx.compose.ui.graphics.Color
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction.ColorPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction.DurationPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction.NumberPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerSideEffect.ItemUpdatedSideEffect
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerState
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerState.ReadyState
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.SideEffect
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.State
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.ColorType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.DurationType
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.NumericType
import com.arakim.datastructurevisualization.ui.mvi.StateReducerWithSideEffect
import com.arakim.datastructurevisualization.ui.util.mapToImmutable
import javax.inject.Inject
import kotlin.time.Duration

class NewDataPickReducer @Inject constructor(
) : StateReducerWithSideEffect<State, GenericPickerAction, NewDataPickedAction, SideEffect>() {

    override fun GenericPickerState.reduce(action: NewDataPickedAction): GenericPickerState = when (action) {
        is DurationPickedAction -> reduceDurationPickedAction(action)
        is ColorPickedAction -> reduceColorPickedAction(action)
        is NumberPickedAction -> reduceNumberPickedAction(action)
    }

    private fun State.reduceDurationPickedAction(action: DurationPickedAction): State = when (this) {
        is ReadyState -> getWithUpdatedItemAndEmitEffect(
            itemId = action.itemId,
            duration = action.duration,
            color = null,
            number = null,
        )

        else -> logInvalidState()
    }

    private fun State.reduceColorPickedAction(
        action: ColorPickedAction,
    ): State = when (this) {
        is ReadyState -> getWithUpdatedItemAndEmitEffect(
            itemId = action.itemId,
            duration = null,
            color = action.color,
            number = null,
        )

        else -> logInvalidState()
    }

    private fun State.reduceNumberPickedAction(action: NumberPickedAction): State = when (this) {
        is ReadyState -> getWithUpdatedItemAndEmitEffect(
            itemId = action.itemId,
            duration = null,
            color = null,
            number = action.number,
        )


        else -> logInvalidState()
    }

    private fun ReadyState.getWithUpdatedItemAndEmitEffect(
        itemId: String,
        duration: Duration?,
        color: Color?,
        number: Number?,
    ): ReadyState {
        val updatedItem = allItems.find { it.id == itemId }?.getUpdated(
            duration = duration,
            color = color,
            number = number,
        )!!

        val updatedAllItems = allItems.mapToImmutable { if (it.id == itemId) updatedItem else it }
        val updatedModalItems = allItems.mapToImmutable { if (it.id == itemId) updatedItem else it }

        emitSideEffect(ItemUpdatedSideEffect(updatedItem))
        return copy(allItems = updatedAllItems, floatingModalItems = updatedModalItems)
    }

    private fun GenericPickerItem<*>.getUpdated(
        duration: Duration?,
        color: Color?,
        number: Number?,
    ): GenericPickerItem<*> =
        when {
            duration != null -> (this as GenericPickerItem<DurationType>).copy(
                pickingDataType = pickingDataType.copy(value = duration),
            )

            color != null -> (this as GenericPickerItem<ColorType>).copy(
                pickingDataType = pickingDataType.copy(value = color),
            )

            number != null -> (this as GenericPickerItem<NumericType>).copy(
                pickingDataType = pickingDataType.copy(value = number),
            )

            else -> throw IllegalStateException("at least one property must be not null")
        }

}


