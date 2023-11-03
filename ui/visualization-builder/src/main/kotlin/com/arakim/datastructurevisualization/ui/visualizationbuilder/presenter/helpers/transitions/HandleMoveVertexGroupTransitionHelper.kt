package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions

import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexMoveType
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexMoveType.MoveBy
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexMoveType.MoveTo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElement
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class HandleMoveVertexGroupTransitionHelper @Inject constructor() {

    suspend fun VisualizationCorePresenter.invoke(
        vertexIdToMove: List<Pair<VertexId, VertexMoveType>>
    ) {
        updateVertexFinalPositions(vertexIdToMove)
        handleGoToFinalPositionTransition(vertexIdToMove.map { it.first })
    }
}

private suspend fun VisualizationCorePresenter.goToFinalPosition(
    vertexId: VertexId
) {
    requireNotNull(vertexStateMap[vertexId]).element.apply {
        isVisible = true
        position.animateTo(
            finalPosition,
            tween(setUp.comparisonTransitionTime.inWholeMilliseconds.toInt())
        )
    }
}

private fun VisualizationCorePresenter.updateVertexFinalPositions(vertexIdToMove: List<Pair<VertexId, VertexMoveType>>) {
    vertexIdToMove.forEach { (vertexId, moveType) ->
        val element = vertexStateMap[vertexId]?.element!!
        element.finalPosition = element.getPositionAfterMove(moveType)
    }
}

private fun VisualizationElement.getPositionAfterMove(moveType: VertexMoveType): DpOffset =
    when (moveType) {
        is MoveBy -> finalPosition + moveType.dpOffset
        is MoveTo -> moveType.dpOffset
    }

private suspend fun VisualizationCorePresenter.handleGoToFinalPositionTransition(
    vertexIds: List<VertexId>
) {
    coroutineScope {
        var lastJob: Job? = null
        vertexIds.forEach { vertexIdToPosition ->
            lastJob = launch {
                goToFinalPosition(vertexIdToPosition)
            }
        }
        lastJob?.join()
    }
}