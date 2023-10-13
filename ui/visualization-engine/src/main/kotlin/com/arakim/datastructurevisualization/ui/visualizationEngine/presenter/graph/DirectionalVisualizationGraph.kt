package com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.model.VisualizationElement


interface DirectionalVisualizationGraph {
    val vertexStateMap: SnapshotStateMap<VertexId, Vertex>
    val vertexConnectionsState: SnapshotStateMap<VertexId, HashSet<VertexId>>

    fun createVertex(vertex: Vertex)
    fun createConnection(from: VertexId, to: VertexId)
}


@JvmInline
value class VertexId(val value: String)

data class Vertex(
    val id: VertexId,
    val element: VisualizationElement,
)

internal class DirectionalVisualizationGraphImpl : DirectionalVisualizationGraph {

    //  It's not best practise to make it public, but here we aim for simplicity and performance
    override val vertexStateMap = mutableStateMapOf<VertexId, Vertex>()
    override val vertexConnectionsState = mutableStateMapOf<VertexId, HashSet<VertexId>>()

    override fun createVertex(vertex: Vertex) {
        vertexStateMap[vertex.id] = vertex
    }

    override fun createConnection(from: VertexId, to: VertexId) {
        val vertex = vertexConnectionsState[from]
        if (vertex == null) {
            vertexConnectionsState[from] = hashSetOf(to)
        } else {
            vertex.add(to)
        }
    }

}
