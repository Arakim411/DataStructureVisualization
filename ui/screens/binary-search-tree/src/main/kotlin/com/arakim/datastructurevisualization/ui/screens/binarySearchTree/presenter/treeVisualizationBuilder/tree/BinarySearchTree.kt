package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree

import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.helpers.HandleTreeNodeDeletionHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId

//TODO increase cohesion
abstract class BinarySearchTree constructor(
    private val handleNodeDeletion: HandleTreeNodeDeletionHelper
) {
    private val listeners = mutableSetOf<BinarySearchTreeListener>()

    lateinit var root: Node

    fun addListener(listener: BinarySearchTreeListener) {
        listeners.add(listener)
    }

    open fun insert(number: Number) {
        if (::root.isInitialized) {
            root.insert(
                number = number,
                traveledNodes = mutableSetOf(root.id),
                onNodeInserted = { node, traveledNodes ->
                    listeners.forEach { listener ->
                        listener.onNodeInserted(
                            node = node,
                            traveledNodes = traveledNodes,
                            rootInsertSide = root.getInsertSide(number)
                        )
                    }
                }
            )
        } else {
            root = Node(
                value = number,
                id = NodeId(number.toDouble().toString()),
                parent = null,
                insertSide = null,
            )

            listeners.forEach {
                it.onRootInserted(root)
            }
        }
    }

    open fun delete(number: Number) = handleNodeDeletion(
        root = root,
        numberToDelete = number,
        listeners = listeners,
    )
}
