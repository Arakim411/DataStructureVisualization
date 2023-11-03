package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree

import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId

internal fun Node.find(number: Number, traveledNode: MutableSet<NodeId>): Node? {
    traveledNode.add(id)
    return when {
        value isEquals number -> this
        number isLessThen value -> left?.find(number, traveledNode)
        else -> right?.find(number, traveledNode)
    }
}


fun Node.getAllNodesUntil(number: Number): Set<Node> {
    val set = mutableSetOf(this)
    right?.getAllNodesUntilRecursive(number, set)
    left?.getAllNodesUntilRecursive(number, set)

    return set
}

private fun Node.getAllNodesUntilRecursive(
    number: Number,
    allAbove: MutableSet<Node>
) {
    when {
        value == number -> Unit
        else -> {
            allAbove.add(this)
            left?.getAllNodesUntilRecursive(number, allAbove)
            right?.getAllNodesUntilRecursive(number, allAbove)
        }
    }
}

internal fun Node.getWithAllChildNodes(): Set<Node> {
    val set = mutableSetOf<Node>(this)

    right?.getAllChildNodesRecursive(set)
    left?.getAllChildNodesRecursive(set)

    return set
}

private fun Node.getAllChildNodesRecursive(set: MutableSet<Node>) {
    set.add(this)
    left?.getAllChildNodesRecursive(set)
    right?.getAllChildNodesRecursive(set)
}

internal fun Node.getNodeWithLowestValue(traveledNodes: MutableSet<NodeId>): Node {
    traveledNodes.add(id)
    return left?.getNodeWithLowestValue(traveledNodes) ?: this
}

fun Node.firstAboveInsertedOnRight(): Node? =
    when (insertSide) {
        Right -> this
        Left -> parent?.firstAboveInsertedOnRight()
        null -> null
    }

fun Node.firstAboveInsertedOnLeft(): Node? =
    when (insertSide) {
        Left -> this
        Right -> parent?.firstAboveInsertedOnLeft()
        null -> null
    }

fun Node.firstWithNumberOnLeft(number: Number): Node? = when {
    number isLessThen value -> this
    else -> right?.firstWithNumberOnLeft(number)
}

fun Node.firstWithNumberOnRight(number: Number): Node? = when {
    number isLessThen value -> left?.firstWithNumberOnRight(number)
    else -> this
}
