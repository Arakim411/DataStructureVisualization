package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helper

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexTransition
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.PriorityQueue
import javax.inject.Inject

class TransitionQueueHelper @Inject constructor() {

    private lateinit var handleTransition: suspend VisualizationCorePresenter.(VertexTransition) -> Unit

    private val transitionQueue: PriorityQueue<VertexTransition> = PriorityQueue(
        compareBy<VertexTransition> {
            it.priority
        }
    )
    private var transitionJob: Job? = null

    fun initialize(
        handleTransition: suspend VisualizationCorePresenter.(VertexTransition) -> Unit
    ) {
        this.handleTransition = handleTransition
    }

    fun addToQueue(transition: VertexTransition) {
        transitionQueue.add(transition)
    }

    fun VisualizationCorePresenter.tryEmptyTransitionQueue() {
        if (!shouldEmptyTransitionQueue()) return

        transitionJob = composeCoroutineScope?.launch {
            emptyTransitionQueue()
        }
    }

    private fun VisualizationCorePresenter.shouldEmptyTransitionQueue(): Boolean =
        (transitionJob?.isActive != true) && composeCoroutineScope != null

    private suspend fun VisualizationCorePresenter.emptyTransitionQueue() {
        var transition = transitionQueue.poll()

        while (transition != null) {
            handleTransition(transition)
            transition = transitionQueue.poll()
        }
    }

}