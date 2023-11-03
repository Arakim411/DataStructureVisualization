package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model

data class Node(
    var value: Number,
    val id: NodeId,
    var parent: Node?,
    var insertSide: InsertSide?,
    var left: Node? = null,
    var right: Node? = null,
) {
    override fun toString(): String {
        return "node: $value left: ${left?.value} right: ${right?.value}"
    }

    override fun hashCode(): Int {
        super.hashCode()
        return value.toInt() % 10
    }
}