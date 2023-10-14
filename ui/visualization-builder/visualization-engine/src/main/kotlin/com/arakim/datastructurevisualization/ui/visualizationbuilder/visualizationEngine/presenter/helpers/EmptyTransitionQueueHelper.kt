package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helpers

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenterImpl
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration

internal fun VisualizationEnginePresenterImpl.tryEmptyTransitionQueue() {
    if (!shouldEmptyQueue()) return

    handleTransitionJob = coroutineScope.launch {
        handleNextTransition()
    }
}

private fun VisualizationEnginePresenterImpl.shouldEmptyQueue() =
    handleTransitionJob?.isActive != true && _currentVertexTransition.subscriptionCount.value > 0

private suspend fun VisualizationEnginePresenterImpl.handleNextTransition() {
    var transition = transitionQueue.poll()
    while (transition != null) {
        _currentVertexTransition.value = transition
        delay(transition.getFullTransitionTime())
        onVertexTransitionFinished(transition)
        transition = transitionQueue.poll()
    }
}

private fun VertexTransition.getFullTransitionTime(): Duration = when (this) {
    is VertexTransition.EnterVertexTransition ->
        vertexTransitionTime + (comparisonTransitionTime * comparisons.size)

    is VertexTransition.MoveVertexTransition -> vertexTransitionTime
}