package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.DirectionalVisualizationGraph
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helper.TransitionHandlerHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helper.TransitionQueueHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.ComparisonState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.ComparisonState.IdleState
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexInfo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexMoveType
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexMoveType.MoveBy
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexMoveType.MoveTo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition.Companion.DefaultPriority
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition.Companion.HighPriority
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition.EnterTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition.MoveTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElement
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationEnginePresenterSetUp
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

typealias Transition = DpOffset

class VisualizationEnginePresenter @Inject constructor(
    private val transitionQueueHelper: TransitionQueueHelper,
    private val transitionHandlerHelper: TransitionHandlerHelper,
) : DirectionalVisualizationGraph() {

    val comparisonState = mutableStateOf<ComparisonState>(IdleState)

    //it's okay until we remember to clear it when composition is finished
    internal var composeCoroutineScope: CoroutineScope? = null

    lateinit var setUp: VisualizationEnginePresenterSetUp

    fun initialize(setUp: VisualizationEnginePresenterSetUp) {
        this.setUp = setUp

        transitionQueueHelper.initialize(
            handleTransition = { with(transitionHandlerHelper) { handleTransition(it) } }
        )
    }

    fun onViewReady(composeCoroutineScope: CoroutineScope) {
        this.composeCoroutineScope = composeCoroutineScope
        with(transitionQueueHelper) { tryEmptyTransitionQueue() }
    }

    fun onViewNotReady() {
        composeCoroutineScope = null
    }

    fun moveVertex(id: VertexId, moveType: VertexMoveType) {
        moveVertexGroup(listOf(id to moveType))
    }

    fun moveVertexGroup(
        vertexIdToMove: List<Pair<VertexId, VertexMoveType>>,
        immediately: Boolean = false,
    ) {
        updateVertexFinalPositions(vertexIdToMove)
        transitionQueueHelper.addToQueue(
            MoveTransition(
                vertexsIdToMove = vertexIdToMove.map { it.first },
                priority = if (immediately) HighPriority else DefaultPriority,
            )
        )
        with(transitionQueueHelper) { tryEmptyTransitionQueue() }
    }

    fun createVertexWithEnterTransition(
        vertexInfo: VertexInfo,
        comparisons: ImmutableList<DpOffset> = immutableListOf(),
    ) {
        super.createVertex(
            vertexInfo = vertexInfo,
            hasEnterTransition = true,
        )
        transitionQueueHelper.addToQueue(
            EnterTransition(
                vertexId = vertexInfo.id,
                comparisons = comparisons,
            )
        )
        with(transitionQueueHelper) { tryEmptyTransitionQueue() }
    }

    private fun updateVertexFinalPositions(vertexIdToMove: List<Pair<VertexId, VertexMoveType>>) {
        vertexIdToMove.forEach { (vertexId, moveType) ->
            val element = vertexStateMap[vertexId]?.element!!
            element.finalPosition = element.getPositionAfterMove(moveType)
        }
    }

    private fun VisualizationElement.getPositionAfterMove(moveType: VertexMoveType): DpOffset =
        when (moveType) {
            is MoveBy -> finalPosition + moveType.dpOffset
            is MoveTo -> moveType.dpOffset
        }

}
