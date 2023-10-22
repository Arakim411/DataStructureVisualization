package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.util.mapToImmutable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.toDpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DefaultVisualizationSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexInfo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexMoveType.MoveBy
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElementShape
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationSetUp
import javax.inject.Inject


//TODO as interface
//TODO simplify with infix funs
//TODO refactor names itd..
//TODO inherit form VisualizationCorePresenter instead of using it as dependency
@Immutable
class VisualizationBuilder @Inject constructor(
    val visualizationCore: VisualizationCorePresenter,
) {
    fun initialize(setUp: VisualizationSetUp = DefaultVisualizationSetUp  ) {
        visualizationCore.initialize(setUp)
    }

    fun createVertex(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
    ) {
        val vertex = getVertexInfo(vertexId, title, position, shape)
        visualizationCore.createVertex(vertex)
    }

    fun createVertexWithEnterTransition(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
        comparisons: ImmutableList<VertexId> = immutableListOf(),
    ) {
        val vertex = getVertexInfo(vertexId, title, position, shape)
        val comparisonsPositions = comparisons.mapToImmutable {
            visualizationCore.getVertex(it)!!.element.finalPosition
        }
        visualizationCore.createVertexWithEnterTransition(vertex, comparisonsPositions)
    }

    fun moveVertexWithConnections(vertexId: VertexId, transition: DpOffset) {
        visualizationCore.apply {
            val connections = getAllConnections(vertexId)

            val vertexIdToMove = (listOf(vertexId) + connections).map { vertexId ->
                vertexId to MoveBy(transition)
            }

            visualizationCore.moveVertexGroup(vertexIdToMove)
        }
    }

    fun createConnection(
        from: VertexId,
        to: VertexId,
    ) = visualizationCore.createConnection(from, to)

    private fun getVertexInfo(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
    ): VertexInfo {

        val getVertexLambda = { id: VertexId ->
            visualizationCore.getVertex(id)
                ?: throw IllegalArgumentException(vertexId.vertexNotFoundErrorMessage())
        }

        return VertexInfo(
            id = vertexId,
            title = title,
            position = position.toDpOffset(getVertexLambda),
            shape = shape,
        )
    }
}

private fun VertexId.vertexNotFoundErrorMessage(): String = "Vertex with id $this not found"



