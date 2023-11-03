package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter

import androidx.compose.runtime.Immutable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.GetVertexInfoHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions.AddTransitionHelper
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.VisualizationCorePresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DefaultVisualizationSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationSetUp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElementShape
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@Immutable
@ViewModelScoped
class VisualizationBuilder @Inject constructor(
    val visualizationCore: VisualizationCorePresenter,
    val addTransitionHelper: AddTransitionHelper,
    private val getVertexInfo: GetVertexInfoHelper,
) {
    fun initialize(setUp: VisualizationSetUp = DefaultVisualizationSetUp) {
        visualizationCore.initialize(setUp)
        addTransitionHelper.initialize(visualizationCore::addTransitionToQueue)
    }

    fun createVertex(
        vertexId: VertexId,
        title: String,
        position: VertexPosition,
        shape: VisualizationElementShape = VisualizationElementShape.Circle,
    ) {
        visualizationCore.apply {
            val vertexInfo = with(getVertexInfo) {
                invoke(
                    vertexId,
                    title,
                    position,
                    shape
                )
            }
            visualizationCore.createVertex(vertexInfo)
        }
    }

    fun createConnection(
        from: VertexId,
        to: VertexId,
    ) = visualizationCore.createConnection(from, to)

}


