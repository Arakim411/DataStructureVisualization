package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model

import androidx.compose.ui.unit.DpOffset

data class VisualizationElement(
    val title: String,
    val position: DpOffset,
    val shape: VisualizationElementShape,
)
