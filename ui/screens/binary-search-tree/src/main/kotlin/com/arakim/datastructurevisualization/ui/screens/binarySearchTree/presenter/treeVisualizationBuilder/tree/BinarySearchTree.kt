package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree

import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.helpers.HandleTreeNodeDeletionHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId

//TODO increase cohesion
abstract class BinarySearchTree constructor(
    private val handleNodeDeletion: HandleTreeNodeDeletionHelper
) {
    private val listeners = mutableSetOf<BinarySearchTreeListener>()

    var root: Node? = null

    init {
        handleNodeDeletion.initialize(this)
    }

    fun addListener(listener: BinarySearchTreeListener) {
        listeners.add(listener)
    }

    open fun insert(number: Number) {
        val localRoot = root
        if (localRoot != null) {
            localRoot.insert(
                number = number,
                traveledNodes = mutableSetOf(localRoot.id),
                onNodeInserted = { node, traveledNodes ->
                    listeners.forEach { listener ->
                        listener.onNodeInserted(
                            node = node,
                            traveledNodes = traveledNodes,
                            rootInsertSide = localRoot.getInsertSide(number)
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
            ).also { root ->
                listeners.forEach {
                    it.onRootInserted(root)
                }
            }

        }
    }

    open fun delete(number: Number) = handleNodeDeletion(
        numberToDelete = number,
        listeners = listeners,
    )
}
