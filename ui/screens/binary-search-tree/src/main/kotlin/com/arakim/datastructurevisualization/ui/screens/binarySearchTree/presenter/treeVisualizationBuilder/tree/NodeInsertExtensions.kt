package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree

import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId
import java.util.UUID

internal fun Node.insert(
    number: Number,
    traveledNodes: MutableSet<NodeId>,
    onNodeInserted: (Node, traveledNodes: Set<NodeId>) -> Unit
) {
    traveledNodes.add(id)
    if (number isLessThen value) {
        insertLeft(number, traveledNodes, onNodeInserted,)
    } else {
        insertRight(number, traveledNodes, onNodeInserted)
    }
}

private fun Node.insertRight(
    number: Number,
    traveledNodes: MutableSet<NodeId>,
    onNodeInserted: (Node,traveledNodes: Set<NodeId>) -> Unit
) {
    when (right) {
        null ->
            right = buildNode(
                parent = this,
                number = number,
            ).also {
                onNodeInserted(it,traveledNodes)
            }

        else -> right!!.insert(number, traveledNodes, onNodeInserted)
    }
}

private fun Node.insertLeft(
    number: Number,
    traveledNodes: MutableSet<NodeId>,
    onNodeInserted: (Node,traveledNodes: Set<NodeId>) -> Unit
) {
    when (left) {
        null ->
            left = buildNode(
                parent = this,
                number = number,
            ).also {
                onNodeInserted(it,traveledNodes)
            }

        else -> left!!.insert(number, traveledNodes, onNodeInserted)
    }
}

private fun buildNode(
    parent: Node,
    number: Number,
): Node = Node(
    value = number,
    id = NodeId(UUID.randomUUID().toString()),
    parent = parent,
    insertSide = if (number.toDouble() < parent.value.toDouble()) Left else Right,
)