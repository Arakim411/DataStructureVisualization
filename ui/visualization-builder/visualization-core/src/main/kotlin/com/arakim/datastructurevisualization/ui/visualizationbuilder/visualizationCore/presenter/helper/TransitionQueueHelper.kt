package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.helper

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.TransitionGroup
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

class TransitionQueueHelper @Inject constructor() {

    private lateinit var handleTransition: suspend VisualizationCorePresenter.(TransitionGroup) -> Unit

    private val transitionQueue: Queue<TransitionGroup> = LinkedList()
    private var transitionJob: Job? = null

    val hasQueuedTransitions get() =  transitionQueue.isNotEmpty().let { it }

    fun initialize(
        handleTransition: suspend VisualizationCorePresenter.(TransitionGroup) -> Unit
    ) {
        this.handleTransition = handleTransition
    }

    fun addToQueue(transition: TransitionGroup) {
        transitionQueue.add(transition)
    }

    fun VisualizationCorePresenter.tryEmptyTransitionQueue() {
        if (!shouldEmptyTransitionQueue() ) return

        transitionJob = composeCoroutineScope?.launch {
            emptyTransitionQueue()
        }
    }

    private fun VisualizationCorePresenter.shouldEmptyTransitionQueue(): Boolean =
        (transitionJob?.isActive != true) && composeCoroutineScope != null

    private suspend fun VisualizationCorePresenter.emptyTransitionQueue() {
        var transition = transitionQueue.peek()

        while (transition != null) {
            handleTransition(transition)
            transitionQueue.poll()
            transition = transitionQueue.peek()
        }
    }

}