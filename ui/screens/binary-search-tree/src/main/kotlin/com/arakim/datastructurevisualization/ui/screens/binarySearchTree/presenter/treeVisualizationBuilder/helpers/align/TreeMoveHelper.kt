package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.align

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.BinarySearchTree
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.firstAboveInsertedOnLeft
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.firstAboveInsertedOnRight
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.getWithAllChildNodes
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.toVertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VertexMoveType.MoveBy

abstract class TreeMoveHelper constructor(
    private val visualizationBuilder: VisualizationBuilder,
) {

    private lateinit var binarySearchTree: BinarySearchTree
    protected var horizontalAlignDistance: Dp = 0.dp

    private val alignForRootLeft = AlignForRootLeft()
    private val alignForRootRight = AlignForRootRight()

    fun initialize(
        binarySearchTree: BinarySearchTree,
        horizontalAlignDistance: Dp,
    ) {
        this.binarySearchTree = binarySearchTree
        this.horizontalAlignDistance = horizontalAlignDistance
    }

    fun alignTreeHorizontally(
        node: Node,
        rootInsertSide: InsertSide,
        distance: Dp
    ) {
        when (rootInsertSide) {
            Left -> alignForRootLeft.alignTreeOnRootLeft(node, distance)
            Right -> alignForRootRight.alignTreeOnRootRight(node, distance)
        }
    }

    private inner class AlignForRootLeft {
        //root left
        fun alignTreeOnRootLeft(
            node: Node,
            distance: Dp,
        ) {
            when (node.insertSide) {
                Right -> alignForInsertedOnRight(node, distance)
                Left -> alignForInsertedOnLeft(node, distance)
                else -> Unit
            }
        }

        //inserted - root: left, parent: right
        private fun alignForInsertedOnRight(node: Node, distance: Dp) {
            val first = node.parent?.firstAboveInsertedOnLeft() ?: return

            visualizationBuilder.addTransitionHelper.moveVertexWithConnectionsTransition(
                vertexId = first.toVertexId(),
                transition = DpOffset(distance, 0.dp)
            )

            alignForInsertedOnLeft(first, distance)
        }

        //root: left, parent: left
        private fun alignForInsertedOnLeft(node: Node, distance: Dp) {
            if (binarySearchTree.root!!.left?.id == node.id) return

            var nodeToMove: Node? = node.firstAboveInsertedOnRight()?.parent ?: return

            while (nodeToMove != null) {
                val nodeToMoves = mutableSetOf(nodeToMove).apply {
                    addAll(nodeToMove!!.left?.getWithAllChildNodes() ?: emptyList())
                    nodeToMove = nodeToMove!!.firstAboveInsertedOnRight()?.parent
                }

                visualizationBuilder.addTransitionHelper.moveVertexGroupTransition(
                    vertexIdToMove = nodeToMoves.map {
                        it.toVertexId() to MoveBy(DpOffset(distance, 0.dp))
                    }
                )
            }
        }
    }

    private inner class AlignForRootRight {

        fun alignTreeOnRootRight(
            node: Node,
            distance: Dp,
        ) {
            when (node.insertSide) {
                Left -> alignForInsertedOnLeft(node, distance)
                Right -> alignForInsertedOnRight(node, distance)
                else -> Unit
            }
        }

        //inserted - root: right, parent: left
        private fun alignForInsertedOnLeft(node: Node, distance: Dp) {
            val first = node.parent?.firstAboveInsertedOnRight() ?: return
            visualizationBuilder.addTransitionHelper.moveVertexWithConnectionsTransition(
                vertexId = first.toVertexId(),
                transition = DpOffset(distance, 0.dp)
            )

            alignForInsertedOnRight(first, distance)
        }

        //root: right, parent: right
        private fun alignForInsertedOnRight(node: Node, distance: Dp) {
            if (binarySearchTree.root!!.right?.id == node.id) return

            var nodeToMove: Node? = node.firstAboveInsertedOnLeft()?.parent ?: return

            while (nodeToMove != null) {
                val nodeToMoves = mutableSetOf(nodeToMove).apply {
                    addAll(nodeToMove!!.right?.getWithAllChildNodes() ?: emptyList())
                    nodeToMove = nodeToMove!!.firstAboveInsertedOnLeft()?.parent
                }

                visualizationBuilder.addTransitionHelper.moveVertexGroupTransition(
                    vertexIdToMove = nodeToMoves.map {
                        it.toVertexId() to MoveBy(DpOffset(distance, 0.dp))
                    }
                )
            }
        }
    }

    private fun moveWithOneSideConnections(
        node: Node,
        distance: Dp,
        side: InsertSide,
    ) {
        val connectionsSide = when (side) {
            Left -> node.left
            Right -> node.right
        }
        val nodesOnSide = connectionsSide?.getWithAllChildNodes()?.toMutableList()

        val vertexIdToMove = mutableSetOf(node).apply {
            addAll(nodesOnSide ?: emptyList())
        }.map {
            it.toVertexId() to MoveBy(DpOffset(distance, 0.dp))
        }

        visualizationBuilder.addTransitionHelper.moveVertexGroupTransition(
            vertexIdToMove = vertexIdToMove
        )
    }
}