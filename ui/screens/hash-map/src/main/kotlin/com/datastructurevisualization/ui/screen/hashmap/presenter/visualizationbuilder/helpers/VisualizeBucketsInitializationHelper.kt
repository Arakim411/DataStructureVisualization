package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers

import androidx.compose.ui.unit.DpOffset
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

class VisualizeBucketsInitializationHelper @Inject constructor(
    private val visualizationBuilder: VisualizationBuilder,
) : HashMapWrapperListener {

    override fun onBucketsInitialized(bucketsCount: Int) {
        addInitialBucket()

        (1..bucketsCount).forEach {
            addBucket(
                previousBucketId = it - 1,
                id = it
            )
        }
    }

    private fun addInitialBucket() {
        //TODO start position should be take from screen size
        visualizationBuilder.createVertex(
            vertexId = 0.toVertexId(),
            title = "0",
            position = VertexPosition.CoordinatesPosition(DpOffset(150.dp, 200.dp)),
            shape = VisualizationElementShape.Square,
        )
    }

    private fun addBucket(previousBucketId: Int, id: Int) {
        visualizationBuilder.createVertex(
            vertexId = id.toVertexId(),
            title = id.toString(),
            position = VertexPosition.RelativePosition(
                relativeVertexId = previousBucketId.toVertexId(),
                relativePositionDistance = RelativePositionDistance.AboveOnRight(
                    aboveDistance = 0.dp,
                    rightDistance = 30.dp,
                ),
            ),
            shape = VisualizationElementShape.Square,
        )
    }
}