package com.arakim.datastructurevisualization.ui.genericPicker.presenter

import androidx.compose.ui.graphics.Color
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import kotlin.time.Duration

sealed interface GenericPickerAction {

    data class InitializedAction(
        val allItems: ImmutableList<GenericPickerItem<*>>,
        val floatingModalItems: ImmutableList<GenericPickerItem<*>>,
    ) : GenericPickerAction

    sealed interface NewDataPickedAction : GenericPickerAction {
        data class DurationPickedAction(
            val itemId: String,
            val duration: Duration,
        ) : NewDataPickedAction

        data class ColorPickedAction(
            val itemId: String,
            val color: Color,
        ) : NewDataPickedAction

        data class NumberPickedAction(
            val itemId: String,
            val number: Number,
        ) : NewDataPickedAction
    }

}