package com.arakim.datastructurevisualization.ui.visualizationbuilder.compose

import androidx.compose.runtime.Composable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilderPresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.VisualizationEngine
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.uiModel.DrawStyle

@Composable
fun VisualizationBuilder(
    visualizationPresenter: VisualizationBuilderPresenter,
) {

    VisualizationEngine(
        presenter = visualizationPresenter.enginePresenter,
        drawStyle = DrawStyle.Default,
    )
}