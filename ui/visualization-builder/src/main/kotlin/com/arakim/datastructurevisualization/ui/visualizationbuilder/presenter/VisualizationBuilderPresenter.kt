package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.toDpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationEnginePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DefaultVisualizationEnginePresenterSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VertexInfo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElementShape
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject


//TODO as interface
//TODO simplify with infix funs
//TODO refactor names itd..
//TODO inherit form VisualizationEnginePresenter instead of using it as dependency
@Immutable
class VisualizationBuilderPresenter @Inject constructor(
    val enginePresenter: VisualizationEnginePresenter,
) {

    fun initialize(
        coroutineScope: CoroutineScope,
    ) {
        enginePresenter.initialize(
            setUp = DefaultVisualizationEnginePresenterSetUp,
        )
    }

    fun createVertex(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
    ) {
        val vertex = getVertexInfo(vertexId, title, position, shape)
        enginePresenter.createVertex(vertex)
    }

    fun createVertexWithEnterTransition(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
        comparisons: ImmutableList<DpOffset> = immutableListOf(),
    ) {
        val vertex = getVertexInfo(vertexId, title, position, shape)
        enginePresenter.createVertexWithEnterTransition(vertex, comparisons)
    }

    fun createConnection(
        from: VertexId,
        to: VertexId,
    ) = enginePresenter.createConnection(from, to)

    private fun getVertexInfo(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
    ): VertexInfo {

        val getVertexLambda = { id: VertexId ->
            enginePresenter.getVertex(id)
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



