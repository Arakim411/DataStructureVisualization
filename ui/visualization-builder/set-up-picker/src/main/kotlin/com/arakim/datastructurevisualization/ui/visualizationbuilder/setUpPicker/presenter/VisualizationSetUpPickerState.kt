package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter

import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.VisualizationSetUpUiModel


sealed interface VisualizationSetUpPickerState {

    object IdleState : VisualizationSetUpPickerState
    object InitializingState : VisualizationSetUpPickerState

    data class ReadyState(val setUp: VisualizationSetUpUiModel): VisualizationSetUpPickerState
}