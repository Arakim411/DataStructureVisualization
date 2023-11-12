package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter

import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem

sealed interface VisualizationSetUpPickerAction {

    object InitializeAction : VisualizationSetUpPickerAction

    data class VisualizationSetUpUpdatedAction(
        val item: GenericPickerItem<*>
    ) : VisualizationSetUpPickerAction
}