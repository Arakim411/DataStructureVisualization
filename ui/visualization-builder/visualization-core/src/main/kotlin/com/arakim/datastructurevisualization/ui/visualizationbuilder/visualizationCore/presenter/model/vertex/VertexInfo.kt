package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex

import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.VertexId

data class VertexInfo(
    val id: VertexId,
    val title: String,
    val position: DpOffset,
    val shape: VisualizationElementShape
)