package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.helpers

import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.BinarySearchTree
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.BinarySearchTreeListener
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.childrenCount
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.find
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.getInsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.getNodeWithLowestValue
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId
import javax.inject.Inject

class HandleTreeNodeDeletionHelper @Inject constructor() {

    lateinit var binarySearchTree: BinarySearchTree

    fun initialize(binarySearchTree: BinarySearchTree) {
        this.binarySearchTree = binarySearchTree
    }

    operator fun invoke(
        numberToDelete: Number,
        listeners: Set<BinarySearchTreeListener>,
    ) {
        val localRoot = binarySearchTree.root ?: return
        val traveledNodes = mutableSetOf<NodeId>()
        val nodeToDelete = localRoot.find(numberToDelete, traveledNodes) ?: return

        val rootInsertSide = when (nodeToDelete.id) {
            localRoot.id -> null
            else -> localRoot.getInsertSide(numberToDelete)
        }
        when (nodeToDelete.childrenCount()) {
            2 -> deleteNodeWith2Children(
                nodeToDelete = nodeToDelete,
                traveledNodes = traveledNodes,
                listeners = listeners,
            )

            1 -> deleteNodeWith1Children(
                nodeToDelete = nodeToDelete,
                listeners = listeners,
                traveledNodes = traveledNodes,
                rootInsertSide = rootInsertSide,
            )

            0 -> deleteNodeWith0Children(
                nodeToDelete = nodeToDelete,
                listeners = listeners,
                traveledNodes = traveledNodes,
                rootInsertSide = rootInsertSide,
            )
        }
    }

    private fun deleteNodeWith2Children(
        nodeToDelete: Node,
        traveledNodes: Set<NodeId>,
        listeners: Set<BinarySearchTreeListener>,
    ) {
        val allTraveledNodes = mutableSetOf<NodeId>()
        allTraveledNodes.addAll(traveledNodes)

        val replacement = nodeToDelete.right!!.getNodeWithLowestValue(allTraveledNodes)

        listeners.forEach {
            it.on2ChildNodeDeleted(
                replacedNode = nodeToDelete,
                traveledNodes = allTraveledNodes,
                replacement = replacement,
            )
        }
        nodeToDelete.value = replacement.value
        when (replacement.childrenCount()) {
            0 -> {
                if (nodeToDelete.id == replacement.parent?.id) {
                    nodeToDelete.right = null
                } else {
                    replacement.parent?.left = null
                }
            }

            1 -> {
                if (nodeToDelete.id == replacement.parent?.id) {
                    nodeToDelete.right = replacement.right
                    replacement.right?.parent = nodeToDelete
                } else {
                    replacement.parent?.left = replacement.right
                    replacement.right?.parent = replacement.parent
                    replacement.right?.insertSide = Left
                }
            }
        }
    }

    private fun deleteNodeWith1Children(
        nodeToDelete: Node,
        listeners: Set<BinarySearchTreeListener>,
        traveledNodes: Set<NodeId>,
        rootInsertSide: InsertSide?,
    ) {
        val child = nodeToDelete.left ?: nodeToDelete.right!!

        if (nodeToDelete.id == binarySearchTree.root?.id) {
            binarySearchTree.root = child
            child.parent = null
        }

        listeners.forEach {
            it.on1ChildNodeDeleted(
                node = nodeToDelete,
                traveledNodes = traveledNodes,
                newConnection = child,
                rootInsertSide = rootInsertSide,
            )
        }
        val parent = nodeToDelete.parent

        if (parent != null) {
            child.parent = parent
            when (parent.left?.value) {
                nodeToDelete.value -> {
                    parent.left = child
                    child.parent = parent
                    child.insertSide = Left
                }

                else -> {
                    parent.right = child
                    child.parent = parent
                    child.insertSide = Right
                }
            }
        }
    }

    private fun deleteNodeWith0Children(
        nodeToDelete: Node,
        listeners: Set<BinarySearchTreeListener>,
        traveledNodes: Set<NodeId>,
        rootInsertSide: InsertSide?,
    ) {
        if (nodeToDelete.id == binarySearchTree.root?.id) {
            binarySearchTree.root = null
        }

        listeners.forEach { listener ->
            listener.on0ChildNodeDeleted(
                node = nodeToDelete,
                traveledNodes = traveledNodes,
                rootInsertSide = rootInsertSide,
            )
        }
        val parent = nodeToDelete.parent

        when (parent?.left?.value) {
            nodeToDelete.value -> parent.left = null
            else -> parent?.right = null
        }
    }
}