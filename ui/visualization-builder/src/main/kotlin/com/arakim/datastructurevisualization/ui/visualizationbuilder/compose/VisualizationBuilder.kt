package com.arakim.datastructurevisualization.ui.visualizationbuilder.compose

import androidx.compose.runtime.Composable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.VisualizationCore

@Composable
fun VisualizationBuilder(
    visualizationPresenter: VisualizationBuilder,
) {

    VisualizationCore(
        presenter = visualizationPresenter.visualizationCore,
    )
}