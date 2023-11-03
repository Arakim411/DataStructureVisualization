package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helper

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.TransitionGroup
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexTransition.ActionTransition
import javax.inject.Inject

class TransitionHandlerHelper @Inject constructor() {

    suspend fun VisualizationCorePresenter.handleTransition(transition: TransitionGroup) =
        handleTransitionGroup(transition)

    private suspend fun VisualizationCorePresenter.handleTransitionGroup(
        transitionGroup: TransitionGroup,
    ) {
        transitionGroup.transitions.forEach { transition ->
            when (transition) {
                is ActionTransition -> handleActionTransition(transition)
            }
        }
    }

    private suspend fun VisualizationCorePresenter.handleActionTransition(transition: ActionTransition) {
        transition.invokeBefore?.invoke(this)
        val transitionScope = TransitionScope(this).apply { invokeTransition(transition.action) }
        transition.invokeAfter?.invoke(this)

        transitionScope.transitionsAfterCurrent.forEach {
            handleTransitionGroup(it)
        }

    }
}

class TransitionScope(
    private val corePresenter: VisualizationCorePresenter,
) {
    private val _transitionsAfterCurrent = mutableSetOf<TransitionGroup>()
    val transitionsAfterCurrent: Set<TransitionGroup> = _transitionsAfterCurrent


    inner class HandleTransitionScope {
        fun addTransitionsAfterCurrent(vararg transitions: VertexTransition) {
            _transitionsAfterCurrent.add(TransitionGroup(transitions.toList()))
        }
    }

    suspend fun invokeTransition(
        unit: suspend HandleTransitionScope.(VisualizationCorePresenter) -> Unit,
    ) {
        HandleTransitionScope().apply {
            unit(corePresenter)
        }
    }

}
