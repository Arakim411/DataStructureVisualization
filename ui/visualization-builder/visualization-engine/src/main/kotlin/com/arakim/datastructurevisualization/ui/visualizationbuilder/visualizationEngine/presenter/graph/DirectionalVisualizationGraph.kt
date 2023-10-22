package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexInfo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElement


abstract class DirectionalVisualizationGraph {
    val vertexStateMap = mutableStateMapOf<VertexId, Vertex>()
    val vertexConnectionsState = mutableStateMapOf<VertexId, HashSet<VertexId>>()

    fun createVertex(vertexInfo: VertexInfo) {
        createVertex(
            vertexInfo = vertexInfo,
            hasEnterTransition = false,
        )
    }

    protected fun createVertex(vertexInfo: VertexInfo, hasEnterTransition: Boolean) {
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
            ),
        )
    }

    fun createConnection(from: VertexId, to: VertexId) {
        val vertex = vertexConnectionsState[from]
        if (vertex == null) {
            vertexConnectionsState[from] = hashSetOf(to)
        } else {
            vertex.add(to)
        }
    }

    fun getVertex(id: VertexId): Vertex? = vertexStateMap[id]
    fun getConnections(id: VertexId): HashSet<VertexId> = vertexConnectionsState[id] ?: hashSetOf()

    fun getAllConnections(vertexId: VertexId): MutableSet<VertexId> {

        val allConnections = mutableSetOf<VertexId>()
        getAllConnectionsRecursive(vertexId, allConnections)
        allConnections.remove(vertexId)

        return allConnections
    }

    private fun getAllConnectionsRecursive(
        vertexId: VertexId,
        connections: MutableSet<VertexId>
    ) {
        getConnections(vertexId).forEach { connectionId ->
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

