package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.Side.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.Side.None
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.Side.Right
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilderPresenter
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance.BelowOnLeft
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance.BelowOnRight
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@ViewModelScoped
class BinarySearchTreeVisualizationBuilder @Inject constructor(
    val visualizationBuilderPresenter: VisualizationBuilderPresenter,
) : BinarySearchTree() {

    fun initialize(coroutineScope: CoroutineScope) {
        visualizationBuilderPresenter.initialize(coroutineScope)
    }

    override fun onInserted(previous: Number?, inserted: Number, side: Side) {
        if (previous == null) {
            visualizationBuilderPresenter.createVertexWithEnterTransition(
                vertexId = VertexId(inserted.toString()),
                position = VertexPosition.CoordinatesPosition(DpOffset(200.dp, 100.dp)),
                title = inserted.toString(),
            )
        } else {
            visualizationBuilderPresenter.createVertexWithEnterTransition(
                vertexId = VertexId(inserted.toString()),
                position = VertexPosition.RelativePosition(
                    relativeVertexId = VertexId(previous.toString()),
                    relativePositionDistance = side.getRelativePosition()
                ),
                title = inserted.toString(),
            )
            visualizationBuilderPresenter.createConnection(
                from = VertexId(previous.toString()),
                to = VertexId(inserted.toString()),
            )
        }
    }

    private fun Side.getRelativePosition(): RelativePositionDistance = when (this) {
        Left -> BelowOnLeft(belowDistance = 50.dp, leftDistance = 35.dp)
        Right -> BelowOnRight(belowDistance = 50.dp, rightDistance = 35.dp)
        None -> TODO()
    }
}