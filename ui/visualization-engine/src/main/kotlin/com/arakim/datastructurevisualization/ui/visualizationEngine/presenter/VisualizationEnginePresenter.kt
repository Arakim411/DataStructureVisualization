package com.arakim.datastructurevisualization.ui.visualizationEngine.presenter

import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.DirectionalVisualizationGraph
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.DirectionalVisualizationGraphImpl
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.Vertex
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.helpers.createEnterTransition
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.helpers.createMoveTransition
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.helpers.tryEmptyTransitionQueue
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.model.PresenterSetUp
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.model.VertexTransition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onSubscription
import java.util.LinkedList
import java.util.Queue

abstract class VisualizationEnginePresenter :
    DirectionalVisualizationGraph by DirectionalVisualizationGraphImpl() {

    internal lateinit var coroutineScope: CoroutineScope
    lateinit var setUp: PresenterSetUp

    abstract val currentVertexTransition: Flow<VertexTransition?>
    abstract fun moveVertex(id: VertexId, newPosition: DpOffset)

    abstract fun createVertexWithEnterTransition(vertex: Vertex, comparisons: ImmutableList<DpOffset>)
    fun createVertexWithEnterTransition(
        vertex: Vertex,
    ) = createVertexWithEnterTransition(vertex, immutableListOf())

    fun initialize(coroutineScope: CoroutineScope, setUp: PresenterSetUp) {
        this.coroutineScope = coroutineScope
        this.setUp = setUp

    }
}

internal class VisualizationEnginePresenterImpl : VisualizationEnginePresenter() {

    internal val _currentVertexTransition = MutableStateFlow<VertexTransition?>(null)
    override val currentVertexTransition: Flow<VertexTransition?> =
        _currentVertexTransition.onSubscription {
            tryEmptyTransitionQueue()
        }

    internal val transitionQueue: Queue<VertexTransition> = LinkedList()
    internal var handleTransitionJob: Job? = null


    override fun moveVertex(id: VertexId, newPosition: DpOffset) {
        val moveTransition = createMoveTransition(id, newPosition)
        transitionQueue.add(moveTransition)
        tryEmptyTransitionQueue()
    }

    override fun createVertexWithEnterTransition(vertex: Vertex, comparisons: ImmutableList<DpOffset>) {
        val enterTransition = createEnterTransition(vertex, comparisons)
        transitionQueue.add(enterTransition)
        tryEmptyTransitionQueue()
    }

    internal fun onVertexTransitionFinished(transitionType: VertexTransition) {
        when (transitionType) {
            is VertexTransition.EnterVertexTransition -> super.createVertex(transitionType.vertex)
            is VertexTransition.MoveVertexTransition -> super.createVertex(transitionType.vertex)
        }
    }
}

