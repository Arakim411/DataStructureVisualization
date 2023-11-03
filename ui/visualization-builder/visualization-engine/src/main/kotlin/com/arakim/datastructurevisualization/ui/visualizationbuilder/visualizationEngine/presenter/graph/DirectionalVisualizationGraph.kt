package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexInfo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElement

//TODO increase cohesion
//TODO refactor
abstract class DirectionalVisualizationGraph {
    //TODO expose immutable collection
    val vertexStateMap = mutableStateMapOf<VertexId, Vertex>()
    val vertexConnectionsState = mutableStateMapOf<VertexId, HashSet<VertexId>>()


    fun createVertex(vertexInfo: VertexInfo, hasEnterTransition: Boolean = false) {
        vertexStateMap[vertexInfo.id] = buildVertex(
            vertexInfo = vertexInfo,
            hasEnterTransition = hasEnterTransition,
        )
    }

    private fun buildVertex(vertexInfo: VertexInfo, hasEnterTransition: Boolean): Vertex {
        return Vertex(
            id = vertexInfo.id,
            element = VisualizationElement(
                title = vertexInfo.title,
                position = Animatable(
                    initialValue = vertexInfo.position,
                    typeConverter = DpOffset.VectorConverter
                ),
                finalPosition = vertexInfo.position,
                shape = vertexInfo.shape,
                isVisible = !hasEnterTransition,
                shotTitle = true,
                showIncomingConnections = !hasEnterTransition
            ),
        )
    }

    fun getVertex(id: VertexId): Vertex? = vertexStateMap[id]
    fun getFinalPosition(id: VertexId): DpOffset? = vertexStateMap[id]?.element?.finalPosition

    fun createConnection(from: VertexId, to: VertexId) {
        val vertex = vertexConnectionsState[from]
        if (vertex == null) {
            vertexConnectionsState[from] = hashSetOf(to)
        } else {
            vertex.add(to)
        }
    }

    fun updateIncomingConnectionsVisibility(vertexId: VertexId, visible: Boolean) {
        val vertex = getVertex(vertexId)!!
        vertexStateMap[vertexId] = vertex.copy(element = vertex.element.copy(showIncomingConnections = visible))
    }

    protected fun passOutGoingConnections(from: VertexId, to: VertexId) {
        val outgoingConnections = getAllOutGoingConnections(from)

        vertexConnectionsState[from]?.clear()
        vertexConnectionsState[to]!!.addAll(outgoingConnections)
    }

    protected fun passIncomingConnections(from: VertexId, to: VertexId) {
        val incomingConnections = getIncomingConnections(from)
        incomingConnections.forEach {
            vertexConnectionsState[it]!!.apply {
                remove(from)
                add(to)
            }
        }
    }

    protected fun changeVisibility(vertexId: VertexId, isVisible: Boolean) {
        val vertex = vertexStateMap[vertexId]
        val element = vertex!!.element

        val updatedElement = element.copy(isVisible = isVisible)
        vertexStateMap[vertexId] = vertex.copy(element = updatedElement)
    }

    fun changeTitleVisibility(vertexId: VertexId, isTitleVisible: Boolean) {
        val vertex = vertexStateMap[vertexId]
        val element = vertex!!.element

        val updatedElement = element.copy(shotTitle = isTitleVisible)
        vertexStateMap[vertexId] = vertex.copy(element = updatedElement)
    }

    fun changeTitle(vertexId: VertexId, newTitle: String) {
        val vertex = vertexStateMap[vertexId]
        val element = vertex!!.element

        val updatedElement = element.copy(title = newTitle)
        vertexStateMap[vertexId] = vertex.copy(element = updatedElement)
    }

    fun removeVertex(vertexId: VertexId) {
        vertexStateMap.remove(vertexId)
        vertexConnectionsState.remove(vertexId)
        vertexConnectionsState.forEach {
            it.value.remove(vertexId)
        }
    }

    fun getIncomingConnections(vertexId: VertexId): Set<VertexId> = vertexConnectionsState
        .filter { it.value.contains(vertexId) }
        .map { it.key }.toSet()

    fun getOutGoingConnections(id: VertexId): Set<VertexId> = vertexConnectionsState[id] ?: hashSetOf()

    //TODO name
    fun getAllOutGoingConnections(vertexId: VertexId): MutableSet<VertexId> {
        val allConnections = hashSetOf<VertexId>()
        getAllConnectionsRecursive(vertexId, allConnections)
        allConnections.remove(vertexId)

        return allConnections
    }

    //TODO name
    private fun getAllConnectionsRecursive(
        vertexId: VertexId,
        connections: MutableSet<VertexId>
    ) {
        getOutGoingConnections(vertexId).forEach { connectionId ->
            if (connections.contains(connectionId)) return@forEach
            connections.add(connectionId)
            getAllConnectionsRecursive(connectionId, connections)
        }
    }
}

@JvmInline
value class VertexId(val value: String)

data class Vertex(
    val id: VertexId,
    val element: VisualizationElement,
)

