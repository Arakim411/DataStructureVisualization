package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers

import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VertexInfo
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElementShape
import javax.inject.Inject

class GetVertexInfoHelper @Inject constructor() {

    fun VisualizationCorePresenter.invoke(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
    ): VertexInfo {
        val getVertexLambda = { id: VertexId ->
            getVertex(id) ?: throw IllegalArgumentException("vertex with $vertexId doesn't exists")
        }

        return VertexInfo(
            id = vertexId,
            title = title,
            position = position.toDpOffset(getVertexLambda),
            shape = shape,
        )
    }
}