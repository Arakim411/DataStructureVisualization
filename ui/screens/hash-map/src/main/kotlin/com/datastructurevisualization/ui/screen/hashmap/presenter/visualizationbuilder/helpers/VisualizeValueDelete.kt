package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.hashmap.HashMapValue
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.hashmap.HashMapWrapperListener
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.toVertexId
import javax.inject.Inject

class VisualizeValueDelete @Inject constructor(
    private val visualizationBuilder: VisualizationBuilder,
) : HashMapWrapperListener {

    override fun onValueDeleted(
        value: HashMapValue,
        valuesInBucketBeforeDeletion: List<HashMapValue>,
        indexOfDeletedValue: Int
    ) {
        visualizationBuilder.visualizationCore.removeVertex(value.id.toVertexId())
        valuesInBucketBeforeDeletion.getOrNull(indexOfDeletedValue + 1)?.also {
            visualizationBuilder.addTransitionHelper.moveVertexWithConnectionsTransition(
                vertexId = it.id.toVertexId(),
                transition = DpOffset(0.dp, 50.dp),
                invokeBefore = {},
                invokeAfter = {},
            )
            val previousElementId = valuesInBucketBeforeDeletion.getOrNull(index = -1)?.id?.toVertexId()
                ?: value.bucket.toVertexId()

            visualizationBuilder.createConnection(previousElementId, it.id.toVertexId())
        }
    }
}