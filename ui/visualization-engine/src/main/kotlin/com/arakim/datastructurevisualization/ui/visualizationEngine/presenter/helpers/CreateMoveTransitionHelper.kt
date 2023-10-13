package com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.helpers

import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.VisualizationEnginePresenterImpl
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.model.VertexTransition

internal fun VisualizationEnginePresenterImpl.createMoveTransition(
    id: VertexId,
    newPosition: DpOffset,
): VertexTransition {
    val vertex = vertexStateMap[id] ?: throw IllegalStateException("Vertex with id $id not found")
    return VertexTransition.MoveVertexTransition(
        vertex = vertex.copy(element = vertex.element.copy(position = newPosition)),
        vertexTransitionTime = setUp.vertexTransitionTime,
    )
}