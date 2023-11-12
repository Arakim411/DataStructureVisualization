package com.arakim.datastructurevisualization.ui.genericPicker.presenter

import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem

sealed interface GenericPickerSideEffect {

    data class ItemUpdatedSideEffect(val item: GenericPickerItem<*>): GenericPickerSideEffect
}