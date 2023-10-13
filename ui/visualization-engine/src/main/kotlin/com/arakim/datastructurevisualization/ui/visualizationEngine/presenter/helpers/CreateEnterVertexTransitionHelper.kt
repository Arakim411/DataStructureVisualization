package com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.helpers

import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.VisualizationEnginePresenterImpl
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.Vertex
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.model.VertexTransition


internal fun VisualizationEnginePresenterImpl.createEnterTransition(
    vertex: Vertex,
    comparisons: ImmutableList<DpOffset>,
): VertexTransition = VertexTransition.EnterVertexTransition(
    vertex = vertex,
    comparisons = comparisons,
    vertexTransitionTime = setUp.vertexTransitionTime,
    comparisonTransitionTime = setUp.comparisonTransitionTime,
)


