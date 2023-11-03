package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions

import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.GetVertexInfoHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexTransition.ActionTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElementShape
import javax.inject.Inject

class CreateEnterTransitionHelper @Inject constructor(
    private val handleComparisons: HandleComparisonsHelper,
    private val getVertexInfoHelper: GetVertexInfoHelper,
) {

    operator fun invoke(
        vertexId: VertexId,
        title: String,
        vertexPosition: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
        comparisons: List<VertexId>,
        invokeBefore: (VisualizationCorePresenter.() -> Unit)?,
        invokeAfter: (VisualizationCorePresenter.() -> Unit)?
    ): ActionTransition = ActionTransition(
        action = { corePresenter ->
            corePresenter.apply {
                val vertex = requireNotNull(vertexStateMap[vertexId])

                val comparisonsPositions = getComparisonsPositions(comparisons)
                vertex.element.apply {
                    showIncomingConnections = false
                    isVisible = true
                    position.snapTo(setUp.enterTransStartPosition)

                    with(handleComparisons) {
                        invoke(comparisonsPositions, shape)
                    }

                    position.animateTo(
                        finalPosition,
                        tween(setUp.vertexTransitionTime.inWholeMilliseconds.toInt())
                    )

                    updateIncomingConnectionsVisibility(vertexId, true)
                }
            }
        },
        invokeBefore = {
            val vertexInfo = with(getVertexInfoHelper) {
                invoke(
                    vertexId = vertexId,
                    title = title,
                    position = vertexPosition,
                    shape = shape
                )
            }
            createVertex(
                vertexInfo = vertexInfo,
                hasEnterTransition = true,
            )
            invokeBefore?.invoke(this)
        },
        invokeAfter = invokeAfter,
    )
}

fun VisualizationCorePresenter.getComparisonsPositions(
    comparisons: List<VertexId>,
): List<DpOffset> = comparisons.map { getVertex(it)!!.element.finalPosition }