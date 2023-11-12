package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.helper.TransitionScope.HandleTransitionScope

sealed interface VertexTransition {
    val invokeBefore: (VisualizationCorePresenter.() -> Unit)?
    val invokeAfter: (VisualizationCorePresenter.() -> Unit)?

    data class ActionTransition(
        val action: suspend HandleTransitionScope.(VisualizationCorePresenter) -> Unit,
        override val invokeBefore: (VisualizationCorePresenter.() -> Unit)? = null,
        override val invokeAfter: (VisualizationCorePresenter.() -> Unit)? = null,
    ) : VertexTransition
}

data class TransitionGroup(
    val transitions: List<VertexTransition>,
)


