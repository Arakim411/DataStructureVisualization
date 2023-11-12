package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter

import androidx.compose.runtime.mutableStateOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.DirectionalVisualizationGraph
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.helper.TransitionHandlerHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.helper.TransitionQueueHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.ComparisonState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.ComparisonState.IdleState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.TextTransitionState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.VisualizationSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.TransitionGroup
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VertexTransition
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class VisualizationCorePresenter @Inject constructor(
    private val transitionQueueHelper: TransitionQueueHelper,
    private val transitionHandlerHelper: TransitionHandlerHelper,
) : DirectionalVisualizationGraph() {

    val comparisonState = mutableStateOf<ComparisonState>(IdleState)
    val textTransitionState = mutableStateOf<TextTransitionState>(TextTransitionState.IdleState)

    //it's okay until we remember to clear it when composition is finished
    internal var composeCoroutineScope: CoroutineScope? = null

    val setUpState = mutableStateOf<VisualizationSetUp?>(null)

    init {
        transitionQueueHelper.initialize(
            handleTransition = { with(transitionHandlerHelper) { handleTransition(it) } }
        )
    }

    fun setVisualizationSetUp(setUp: VisualizationSetUp) {
        setUpState.value = setUp
        with(transitionQueueHelper) { tryEmptyTransitionQueue() }
    }

    fun onViewReady(composeCoroutineScope: CoroutineScope) {
        this.composeCoroutineScope = composeCoroutineScope
        with(transitionQueueHelper) { tryEmptyTransitionQueue() }
    }

    fun onViewNotReady() {
        composeCoroutineScope = null
    }

    fun addTransitionToQueue(vararg actionTransitions: VertexTransition) {
        transitionQueueHelper.addToQueue(TransitionGroup(actionTransitions.toList()))
        with(transitionQueueHelper) { tryEmptyTransitionQueue() }
    }
}
