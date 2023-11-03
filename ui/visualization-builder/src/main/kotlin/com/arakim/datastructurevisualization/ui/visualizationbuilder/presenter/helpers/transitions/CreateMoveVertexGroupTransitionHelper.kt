package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions

import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexMoveType
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexTransition.ActionTransition
import javax.inject.Inject

class CreateMoveVertexGroupTransitionHelper @Inject constructor(
    private val handleMoveVertexGroup: HandleMoveVertexGroupTransitionHelper,
) {

    operator fun invoke(
        vertexIdToMove: List<Pair<VertexId, VertexMoveType>>,
        invokeBefore: (VisualizationCorePresenter.() -> Unit)?,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)?
    ): ActionTransition = ActionTransition(
        action = { corePresenter ->
            corePresenter.apply {
                with(handleMoveVertexGroup) { invoke(vertexIdToMove) }
            }
        },
        invokeBefore = invokeBefore,
        invokeAfter = invokeAfter,
    )
}
