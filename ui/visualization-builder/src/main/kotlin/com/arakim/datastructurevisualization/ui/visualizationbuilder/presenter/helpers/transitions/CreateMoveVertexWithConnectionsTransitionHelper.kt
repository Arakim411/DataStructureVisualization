package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexMoveType
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexTransition.ActionTransition
import javax.inject.Inject

class CreateMoveVertexWithConnectionsTransitionHelper @Inject constructor(
    private val handleMoveVertexGroup: HandleMoveVertexGroupTransitionHelper,
) {

    operator fun invoke(
        vertexId: VertexId,
        moveType: VertexMoveType,
        invokeBefore: (VisualizationCorePresenter.() -> Unit)?,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)?
    ): ActionTransition = ActionTransition(
        action = { corePresenter ->
            corePresenter.apply {
                val vertexIdToMove = getConnectionsToMoveType(vertexId, moveType)
                with(handleMoveVertexGroup) { invoke(vertexIdToMove) }
            }
        },
        invokeBefore = invokeBefore,
        invokeAfter = invokeAfter,
    )
}

private fun VisualizationCorePresenter.getConnectionsToMoveType(
    vertexId: VertexId,
    moveType: VertexMoveType,
): List<Pair<VertexId, VertexMoveType>> {
    val connections = getAllOutGoingConnections(vertexId)
    return (listOf(vertexId) + connections).map { id ->
        id to moveType
    }
}


