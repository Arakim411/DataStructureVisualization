package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter

import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.VisualizationSetUpUiModel

sealed interface VisualizationSetUpPickerAction {

    sealed interface InitializationAction : VisualizationSetUpPickerAction {
        data class InitializeAction(val id: Int) : InitializationAction
        data class SetUpUpdatedAction(val setUp: VisualizationSetUpUiModel) : InitializationAction
    }

    data class VisualizationSetUpUpdatedAction(
        val item: GenericPickerItem<*>
    ) : VisualizationSetUpPickerAction
}