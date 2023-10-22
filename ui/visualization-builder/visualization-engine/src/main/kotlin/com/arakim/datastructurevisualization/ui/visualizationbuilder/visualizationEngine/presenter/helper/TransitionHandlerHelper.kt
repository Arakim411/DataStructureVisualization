package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helper

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.ComparisonState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.ComparisonState.IdleState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition.EnterTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition.MoveTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElementShape
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TransitionHandlerHelper @Inject constructor() {

    suspend fun VisualizationEnginePresenter.handleTransition(transition: VertexTransition) =
        when (transition) {
            is EnterTransition -> handleEnterTransition(transition)
            is MoveTransition -> handleMoveTransition(transition)
        }

    private suspend fun VisualizationEnginePresenter.handleEnterTransition(enterTransition: EnterTransition) {
        val vertex = requireNotNull(vertexStateMap[enterTransition.vertexId])

        vertex.element.apply {
            isVisible = true
            position.snapTo(setUp.enterTransStartPosition)

            handleComparisons(enterTransition.comparisons, shape)

            position.animateTo(
                finalPosition,
                tween(setUp.vertexTransitionTime.inWholeMilliseconds.toInt())
            )
        }
    }

    private suspend fun VisualizationEnginePresenter.handleComparisons(
        comparisons: List<DpOffset>,
        shape: VisualizationElementShape,
    ) {
        comparisons.firstOrNull()?.also { firstPosition ->
            val anim = Animatable(initialValue = firstPosition, DpOffset.VectorConverter)

            comparisonState.value = ComparisonState.ComparingState(anim, shape)
            comparisons.drop(1).forEach { position ->
                anim.animateTo(
                    position,
                    tween(durationMillis = setUp.comparisonTransitionTime.inWholeMilliseconds.toInt())
                )
            }
            comparisonState.value = IdleState
        }
    }

    private suspend fun VisualizationEnginePresenter.handleMoveTransition(moveTransition: MoveTransition) {
        coroutineScope {
            var lastJob: Job? = null
            moveTransition.vertexsIdToMove.forEach { vertexIdToPosition ->
                lastJob = launch {
                    handleMove(vertexIdToPosition)
                }
            }
            lastJob?.join()
        }
    }

    private suspend fun VisualizationEnginePresenter.handleMove(
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
}