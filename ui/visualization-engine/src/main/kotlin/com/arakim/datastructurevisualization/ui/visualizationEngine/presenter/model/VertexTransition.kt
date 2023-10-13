package com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.Vertex
import kotlin.time.Duration

@Immutable
sealed interface VertexTransition {

    val vertex: Vertex
    val vertexTransitionTime: Duration

    @Immutable
    data class EnterVertexTransition(
        override val vertex: Vertex,
        val comparisons: ImmutableList<DpOffset>,
        override val vertexTransitionTime: Duration,
        val comparisonTransitionTime: Duration,
    ) : VertexTransition

    @Immutable
    data class MoveVertexTransition(
        override val vertex: Vertex,
        override val vertexTransitionTime: Duration,
    ) : VertexTransition
}