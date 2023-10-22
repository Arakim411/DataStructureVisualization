package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helper

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.ComparisonState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.ComparisonState.IdleState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexTransition.EnterTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexTransition.GoToFinalPositionTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElementShape
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TransitionHandlerHelper @Inject constructor() {

    suspend fun VisualizationCorePresenter.handleTransition(transition: VertexTransition) =
        when (transition) {
            is EnterTransition -> handleEnterTransition(transition)
            is GoToFinalPositionTransition -> handleGoToFinalPositionTransition(transition)
        }

    private suspend fun VisualizationCorePresenter.handleEnterTransition(enterTransition: EnterTransition) {
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

    private suspend fun VisualizationCorePresenter.handleComparisons(
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

    private suspend fun VisualizationCorePresenter.handleGoToFinalPositionTransition(
        goToFinalPositionTransition: GoToFinalPositionTransition,
    ) {
        coroutineScope {
            var lastJob: Job? = null
            goToFinalPositionTransition.vertexsIds.forEach { vertexIdToPosition ->
                lastJob = launch {
                    goToFinalPosition(vertexIdToPosition)
                }
            }
            lastJob?.join()
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
}