package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers

import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VisualizationElementShape
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.hashmap.HashMapValue
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.hashmap.HashMapWrapperListener
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.toVertexId
import javax.inject.Inject

class VisualizeValueAdd @Inject constructor(
    private val visualizationBuilder: VisualizationBuilder,
) : HashMapWrapperListener {

    override fun onValueAdded(value: HashMapValue, valuesInBucket: List<HashMapValue>) {
        val previousElementId = getPreviousElementId(value, valuesInBucket)
        visualizationBuilder.addTransitionHelper.createVertexWithEnterTransition(
            vertexId = value.id.toVertexId(),
            title = value.value.toString(),
            position = VertexPosition.RelativePosition(
                relativeVertexId = previousElementId,
                relativePositionDistance = RelativePositionDistance.AboveOnRight(
                    aboveDistance = 50.dp,
                    rightDistance = 0.dp,
                ),
            ),
            shape = VisualizationElementShape.Square,
        )

        visualizationBuilder.createConnection(
            from = previousElementId,
            to = value.id.toVertexId(),
        )

    }

    private fun getPreviousElementId(value: HashMapValue, valuesInBucket: List<HashMapValue>): VertexId {
        if (valuesInBucket.isEmpty()) return value.bucket.toVertexId()
        return valuesInBucket.last().id.toVertexId()
    }
}

