package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter

import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.DirectionalVisualizationGraph
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.DirectionalVisualizationGraphImpl
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.Vertex
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helpers.createEnterTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helpers.createMoveTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.helpers.tryEmptyTransitionQueue
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexTransition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationEnginePresenterSetUp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onSubscription
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

// TODO adapt to allows wrapper inherit from it instead of using as dependency
abstract class VisualizationEnginePresenter :
    DirectionalVisualizationGraph by DirectionalVisualizationGraphImpl() {

    internal lateinit var coroutineScope: CoroutineScope
    lateinit var setUp: VisualizationEnginePresenterSetUp

    abstract val currentVertexTransition: Flow<VertexTransition?>
    abstract fun moveVertex(id: VertexId, newPosition: DpOffset)

    abstract fun getVertex(id: VertexId): Vertex?

    abstract fun createVertexWithEnterTransition(vertex: Vertex, comparisons: ImmutableList<DpOffset>)
    fun createVertexWithEnterTransition(
        vertex: Vertex,
    ) = createVertexWithEnterTransition(vertex, immutableListOf())

    fun initialize(coroutineScope: CoroutineScope, setUp: VisualizationEnginePresenterSetUp) {
        this.coroutineScope = coroutineScope
        this.setUp = setUp

    }
}

internal class VisualizationEnginePresenterImpl @Inject constructor() : VisualizationEnginePresenter() {

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

    // TODO very bad approach improve
    override fun getVertex(id: VertexId): Vertex? =
        vertexStateMap[id] ?: transitionQueue.getVertex(id) ?: _currentVertexTransition.getVertex(id)

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


private fun Queue<VertexTransition>.getVertex(
    id: VertexId,
): Vertex? = find { it.vertex.id == id }?.vertex

private fun MutableStateFlow<VertexTransition?>.getVertex(id: VertexId) = value.let { transition ->
    when (transition?.vertex?.id) {
        id -> transition.vertex
        else -> null
    }
}
