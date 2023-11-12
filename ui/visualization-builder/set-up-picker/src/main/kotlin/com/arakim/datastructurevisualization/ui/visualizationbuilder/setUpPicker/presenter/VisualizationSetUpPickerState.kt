package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.VisualizationSetUp

sealed interface VisualizationSetUpPickerState {

    object IdleState : VisualizationSetUpPickerState

    data class ReadyState(val setUp: VisualizationSetUp): VisualizationSetUpPickerState
}