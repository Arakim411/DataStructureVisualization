package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helper

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.PriorityQueue
import javax.inject.Inject

class TransitionQueueHelper @Inject constructor() {

    private lateinit var coroutineScope: CoroutineScope
    private lateinit var handleTransition: suspend VisualizationEnginePresenter.(VertexTransition) -> Unit


    private val transitionQueue: PriorityQueue<VertexTransition> = PriorityQueue(
        compareBy<VertexTransition> {
            it.priority
        }
    )
    private var transitionJob: Job? = null

    fun initialize(
        coroutineScope: CoroutineScope,
        handleTransition: suspend VisualizationEnginePresenter.(VertexTransition) -> Unit
    ) {
        this.coroutineScope = coroutineScope
        this.handleTransition = handleTransition
    }

    fun addToQueue(transition: VertexTransition) {
        transitionQueue.add(transition)
    }

    fun VisualizationEnginePresenter.tryEmptyTransitionQueue() {
        if (!shouldEmptyTransitionQueue()) return

        transitionJob = coroutineScope.launch {
            emptyTransitionQueue()
        }
    }

    //TODO don't empty queue when view is not collecting
    private fun shouldEmptyTransitionQueue(): Boolean = transitionJob?.isActive != true

    private suspend fun VisualizationEnginePresenter.emptyTransitionQueue() {
        var transition = transitionQueue.poll()

        while (transition != null) {
            handleTransition(transition)
            transition = transitionQueue.poll()
        }
    }

}