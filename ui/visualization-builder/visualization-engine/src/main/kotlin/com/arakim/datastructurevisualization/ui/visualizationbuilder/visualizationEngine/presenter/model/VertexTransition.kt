package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model

import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId

sealed interface VertexTransition {

    data class EnterTransition(
        val vertexId: VertexId,
        val comparisons: List<DpOffset>,
    ) : VertexTransition

    data class MoveTransition(val vertexGroup: List<Pair<VertexId, DpOffset>>) : VertexTransition
}