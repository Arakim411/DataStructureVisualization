package com.arakim.datastructurevisualization.ui.visualizationbuilder.compose

import androidx.compose.runtime.Composable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.compose.VisualizationSetUpPickerView
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.VisualizationCore

@Composable
fun VisualizationBuilderView(
    visualizationPresenter: VisualizationBuilder,
) {

    VisualizationSetUpPickerView(presenter = visualizationPresenter.setUpPickerPresenter) {

        VisualizationCore(
            presenter = visualizationPresenter.visualizationCore,
        )
    }
}