package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.align

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.toVertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AlignNodeDeletion @Inject constructor(
    private val visualizationBuilder: VisualizationBuilder,
) : TreeMoveHelper(visualizationBuilder) {

    fun align0ChildNodeDeleted(
        node: Node,
        rootInsertSide: InsertSide
    ) {
        when (rootInsertSide) {
            Left -> {
                alignTreeHorizontally(
                    node = node,
                    rootInsertSide = rootInsertSide,
                    distance = horizontalAlignDistance,
                )
            }

            Right -> {
                alignTreeHorizontally(
                    node = node,
                    rootInsertSide = rootInsertSide,
                    distance = -horizontalAlignDistance,
                )
            }
        }
    }

    fun align1ChildNodeDeleted(
        node: Node,
        newConnection: NodeId,
        rootInsertSide: InsertSide,
        deletedNodePosition: DpOffset,
    ) {
        visualizationBuilder.visualizationCore.createConnection(
            node.parent!!.toVertexId(),
            newConnection.toVertexId()
        )

        alignTreeHorizontally(
            node = node,
            rootInsertSide = rootInsertSide,
            distance = if (rootInsertSide == Left) horizontalAlignDistance else -horizontalAlignDistance,
        )

        moveToOtherNodePosition(
            deletedNode = node,
            newConnection = newConnection,
            deletedNodePosition = deletedNodePosition,
            rootInsertSide = rootInsertSide,
        )
    }

    fun moveToOtherNodePosition(
        deletedNode: Node,
        newConnection: NodeId,
        deletedNodePosition: DpOffset,
        rootInsertSide: InsertSide,
    ) {

        val newConnectionPosition = getFinalPosition(newConnection.toVertexId())
        val parentPosition = getFinalPosition(deletedNode.parent?.id?.toVertexId()!!)

        val differenceY = deletedNodePosition.y - newConnectionPosition.y
        var differenceX = parentPosition.x - newConnectionPosition.x

        differenceX += when (deletedNode.insertSide) {
            Left -> -horizontalAlignDistance
            Right -> horizontalAlignDistance
            null -> 0.dp
        }

        visualizationBuilder.addTransitionHelper.moveVertexWithConnectionsTransition(
            newConnection.toVertexId(),
            DpOffset(
                differenceX,
                differenceY,
            ),
        )
    }
    private fun getFinalPosition(
        vertexId: VertexId
    ): DpOffset = visualizationBuilder.visualizationCore.getFinalPosition(vertexId)!!
}