package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.align

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.isLessThen
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.toVertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.VertexId
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AlignNodeDeletion @Inject constructor(
    private val visualizationBuilder: VisualizationBuilder,
) : TreeMoveHelper(visualizationBuilder) {

    fun align0ChildNodeDeleted(
        node: Node,
        rootInsertSide: InsertSide?
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
            else -> {}
        }
    }

    fun align1ChildNodeDeleted(
        node: Node,
        newConnection: Node,
        rootInsertSide: InsertSide?,
        deletedNodePosition: DpOffset,
    ) {


        node.parent?.also { parent ->
            visualizationBuilder.visualizationCore.createConnection(
                parent.toVertexId(),
                newConnection.toVertexId()
            )
        }

        rootInsertSide?.also {
            alignTreeHorizontally(
                node = node,
                rootInsertSide = it,
                distance = if (rootInsertSide == Left) horizontalAlignDistance else -horizontalAlignDistance,
            )
        }
        alignNewConnectionNode(
            deletedNode = node,
            newConnection = newConnection,
            deletedNodePosition = deletedNodePosition,
            rootInsertSide = rootInsertSide,
        )
    }

    private fun alignNewConnectionNode(
        deletedNode: Node,
        newConnection: Node,
        deletedNodePosition: DpOffset,
        rootInsertSide: InsertSide?,
    ) {

        val newConnectionPosition = getFinalPosition(newConnection.toVertexId())

        val y = deletedNodePosition.y - newConnectionPosition.y
        var x = when (rootInsertSide) {
            Left -> getXForRootLeft(
                deletedNode = deletedNode,
                newConnection = newConnection,
            )

            Right -> getXForRootRight(
                deletedNode = deletedNode,
                newConnection = newConnection,
            )

            null -> deletedNodePosition.x - newConnectionPosition.x
        }

        visualizationBuilder.addTransitionHelper.moveVertexWithConnectionsTransition(
            vertexId = newConnection.toVertexId(),
            transition = DpOffset(
                x = x,
                y = y,
            ),
        )
    }

    private fun getXForRootRight(deletedNode: Node, newConnection: Node): Dp =
        when (deletedNode.insertSide) {
            Left -> when {
                newConnection.value isLessThen deletedNode.value -> horizontalAlignDistance
                else -> 0.dp
            }

            Right -> when {
                newConnection.value isLessThen deletedNode.value -> 0.dp
                else -> -horizontalAlignDistance
            }

            null -> 0.dp
        }

    private fun getXForRootLeft(deletedNode: Node, newConnection: Node): Dp =
        when (deletedNode.insertSide) {
            Left -> when {
                newConnection.value isLessThen deletedNode.value -> horizontalAlignDistance
                else -> 0.dp
            }

            Right -> when {
                newConnection.value isLessThen deletedNode.value -> 0.dp
                else -> -horizontalAlignDistance
            }

            null -> 0.dp
        }


    private fun getFinalPosition(
        vertexId: VertexId
    ): DpOffset = visualizationBuilder.visualizationCore.getFinalPosition(vertexId)!!
}