package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex

import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId

sealed interface VertexTransition {
    val priority: Int

    data class EnterTransition(
        override val priority: Int = DefaultPriority,
        val vertexId: VertexId,
        val comparisons: List<DpOffset>,
    ) : VertexTransition

    data class GoToFinalPositionTransition(
        override val priority: Int = DefaultPriority,
        val vertexsIds: List<VertexId>,
    ) : VertexTransition

    companion object {
        const val DefaultPriority = 0
        const val HighPriority = 1
    }
}
