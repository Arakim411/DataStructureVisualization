package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.toDpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.Vertex
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DefaultVisualizationEnginePresenterSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElement
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElementShape
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


//TODO as interface
//TODO simplify with infix funs
//TODO refactor names itd..
@Immutable
class VisualizationBuilderPresenter @Inject constructor(
    val enginePresenter: VisualizationEnginePresenter,
) {

    fun initialize(
        coroutineScope: CoroutineScope,
    ) {
        enginePresenter.initialize(
            coroutineScope = coroutineScope,
            setUp = DefaultVisualizationEnginePresenterSetUp,
        )
    }

    fun createVertex(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
    ) {
        val vertex = getVertex(vertexId, title, position, shape)
        enginePresenter.createVertex(vertex)
    }

    fun createVertexWithEnterTransition(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
        comparisons: ImmutableList<DpOffset> = immutableListOf(),
    ) {
        val vertex = getVertex(vertexId, title, position, shape)
        enginePresenter.createVertexWithEnterTransition(vertex, comparisons)
    }

    fun createConnection(
        from: VertexId,
        to: VertexId,
    ) = enginePresenter.createConnection(from, to)

    private fun getVertex(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
    ): Vertex {
        val getVertexLambda = { id: VertexId ->
            enginePresenter.getVertex(id)
                ?: throw IllegalArgumentException(vertexId.vertexNotFoundErrorMessage())
        }

        val element = VisualizationElement(
            title = title,
            position = position.toDpOffset(getVertexLambda),
            shape = shape,
        )

        return Vertex(
            id = vertexId,
            element = element
        )
    }
}

private fun VertexId.vertexNotFoundErrorMessage(): String = "Vertex with id $this not found"



