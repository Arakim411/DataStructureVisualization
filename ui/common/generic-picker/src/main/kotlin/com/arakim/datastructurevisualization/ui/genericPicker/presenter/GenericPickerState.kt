package com.arakim.datastructurevisualization.ui.genericPicker.presenter

import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import javax.annotation.concurrent.Immutable

@Immutable
sealed interface GenericPickerState {

    object IdleState : GenericPickerState

    data class ReadyState(
        val allItems: ImmutableList<GenericPickerItem<*>>,
        val floatingModalItems: ImmutableList<GenericPickerItem<*>>,
    ) : GenericPickerState {

    }
}