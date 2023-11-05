package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree

import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId

interface BinarySearchTreeListener {

    fun onNodeInserted(
        node: Node,
        rootInsertSide: InsertSide,
        traveledNodes: Set<NodeId>,
    ) = Unit

    fun onRootInserted(node: Node) = Unit

    fun on0ChildNodeDeleted(
        node: Node,
        traveledNodes: Set<NodeId>,
        rootInsertSide: InsertSide?,
    ) = Unit


    fun on1ChildNodeDeleted(
        node: Node,
        traveledNodes: Set<NodeId>,
        newConnection: Node,
        rootInsertSide: InsertSide?,
    ) = Unit

    fun on2ChildNodeDeleted(
        replacedNode: Node,
        traveledNodes: Set<NodeId>,
        replacement: Node,
    ) = Unit
}