package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.align.TreeAlignHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.BinarySearchTreeListener
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.title
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.toVertexId
import com.arakim.datastructurevisualization.ui.util.mapToImmutable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance.BelowOnLeft
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance.BelowOnRight
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class VisualizeNodeInsertedHelper @Inject constructor(
    private val visualizationBuilder: VisualizationBuilder,
    private val treeAlignHelper: TreeAlignHelper,
) : BinarySearchTreeListener {

    private var elementHorizontalDistance: Dp = 0.dp
    private var elementVerticalDistance: Dp = 0.dp

    fun initialize(
        elementHorizontalDistance: Dp,
        elementVerticalDistance: Dp,
    ) {
        this.elementHorizontalDistance = elementHorizontalDistance
        this.elementVerticalDistance = elementVerticalDistance
    }

    override fun onNodeInserted(
        node: Node,
        rootInsertSide: InsertSide,
        traveledNodes: Set<NodeId>,
    ) {
        visualizationBuilder.addTransitionHelper.createVertexWithEnterTransition(
            vertexId = node.toVertexId(),
            position = VertexPosition.RelativePosition(
                relativeVertexId = node.parent!!.toVertexId(),
                relativePositionDistance = node.insertSide!!.getRelativePosition()
            ),
            title = node.title(),
            comparisons = traveledNodes.mapToImmutable { it.toVertexId() },
            invokeBefore = {
                visualizationBuilder.createConnection(
                    from = node.parent!!.toVertexId(),
                    to = node.toVertexId(),
                )
            }
        )


        treeAlignHelper.alignNodeInsertion.alignTreeAfterInsertion(node, rootInsertSide)
    }

    override fun onRootInserted(node: Node) {
        visualizationBuilder.createVertex(
            vertexId = node.toVertexId(),
            //TODO should be at middle of screen, compose needs to initialize it
            position = VertexPosition.CoordinatesPosition(DpOffset(200.dp, 100.dp)),
            title = node.title(),
        )
    }

    private fun InsertSide.getRelativePosition(): RelativePositionDistance = when (this) {
        Left -> BelowOnLeft(
            belowDistance = elementVerticalDistance,
            leftDistance = elementHorizontalDistance,
        )

        Right -> BelowOnRight(
            belowDistance = elementVerticalDistance,
            rightDistance = elementHorizontalDistance
        )
    }
}