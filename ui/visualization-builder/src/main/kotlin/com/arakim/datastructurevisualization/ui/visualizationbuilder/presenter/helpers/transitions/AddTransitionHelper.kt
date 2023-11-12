package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions

import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.helper.TransitionScope.HandleTransitionScope
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VertexMoveType
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VertexMoveType.MoveBy
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VertexTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VertexTransition.ActionTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VisualizationElementShape
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VisualizationElementShape.Circle
import javax.inject.Inject


class AddTransitionHelper @Inject constructor(
    private val createEnterTransition: CreateEnterTransitionHelper,
    private val createMoveVertexGroupConnectionsTransition: CreateMoveVertexWithConnectionsTransitionHelper,
    private val createMoveVertexGroupTransition: CreateMoveVertexGroupTransitionHelper,
    private val createMoveTextTransition: CreateMoveTextTransitionHelper,
    val handleComparisons: HandleComparisonsHelper,
) {
    private lateinit var addTransition: (vararg: VertexTransition) -> Unit

    fun initialize(addTransition: (vararg: VertexTransition) -> Unit) {
        this.addTransition = addTransition
    }

    fun addActionTransition(action: suspend HandleTransitionScope.(VisualizationCorePresenter) -> Unit) {
        addTransition(
            ActionTransition(
                action = action
            )
        )
    }

    fun createVertexWithEnterTransition(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = Circle,
        comparisons: ImmutableList<VertexId> = immutableListOf(),
        invokeBefore: (VisualizationCorePresenter.() -> Unit)? = null,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)? = null,
    ) {
        addTransition(
            createEnterTransition(
                vertexId = vertexId,
                title = title,
                vertexPosition = position,
                shape = shape,
                comparisons = comparisons,
                invokeBefore = invokeBefore,
                invokeAfter = invokeAfter,
            )
        )
    }

    fun moveTextTransition(
        from: VertexId,
        to: VertexId,
        invokeBefore: (VisualizationCorePresenter.() -> Unit)? = null,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)? = null,
    ) {
        addTransition(
            createMoveTextTransition(
                from = from,
                to = to,
                invokeBefore = invokeBefore,
                invokeAfter = invokeAfter,
            ),
        )
    }

    fun moveVertexWithConnectionsTransition(
        vertexId: VertexId,
        transition: DpOffset,
        invokeBefore: (VisualizationCorePresenter.() -> Unit)? = null,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)? = null,
    ) {
        addTransition(
            createMoveVertexGroupConnectionsTransition(
                vertexId = vertexId,
                moveType = MoveBy(transition),
                invokeBefore = invokeBefore,
                invokeAfter = invokeAfter,
            )
        )
    }

    fun moveVertexTransition(
        vertexId: VertexId,
        moveType: VertexMoveType,
        invokeBefore: (VisualizationCorePresenter.() -> Unit)? = null,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)? = null,
    ) {
        moveVertexGroupTransition(
            vertexIdToMove = listOf(vertexId to moveType),
            invokeBefore = invokeBefore,
            invokeAfter = invokeAfter,
        )
    }

    fun moveVertexGroupTransition(
        vertexIdToMove: List<Pair<VertexId, VertexMoveType>>,
        invokeBefore: (VisualizationCorePresenter.() -> Unit)? = null,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)? = null,
    ) {
        addTransition(
            createMoveVertexGroupTransition(
                vertexIdToMove = vertexIdToMove,
                invokeBefore = invokeBefore,
                invokeAfter = invokeAfter,
            )
        )
    }

    fun removeVertexTransition(
        vertexId: VertexId,
        comparisons: ImmutableList<VertexId>,
        invokeBefore: (VisualizationCorePresenter.() -> Unit)? = null,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)? = null,
    ) {
        addTransition(
            ActionTransition(
                action = { corePresenter ->
                    corePresenter.apply {
                        with(handleComparisons) {
                            invoke(getComparisonsPositions(comparisons), Circle)
                        }
                        removeVertex(vertexId)
                    }
                },
                invokeBefore = invokeBefore,
                invokeAfter = invokeAfter
            )
        )
    }
}
