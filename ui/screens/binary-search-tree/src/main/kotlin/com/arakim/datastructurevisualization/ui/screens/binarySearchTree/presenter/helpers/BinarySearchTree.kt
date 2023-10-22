package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers

import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.NodeId
import java.util.UUID

abstract class BinarySearchTree {

    private lateinit var root: Node

    fun insert(number: Number) {
        if (::root.isInitialized) {
            root.insert(
                number = number,
                traveledNodeIds = mutableSetOf(root.id)
            )
        } else {
            root = Node(
                value = number,
                id = NodeId(number.toDouble().toString()),
                parent = null,
                insertSide = null,
            )
            onRootInserted(root)
        }
    }

    private fun Node.insert(number: Number, traveledNodeIds: MutableSet<NodeId>) {
        traveledNodeIds.add(id)
        if (number isLessThen value) {
            insertLeft(number, traveledNodeIds)
        } else {
            insertRight(number, traveledNodeIds)
        }
    }


    private fun Node.insertRight(number: Number, traveledNodes: MutableSet<NodeId>) {
        when (right) {
            null ->
                right = buildNode(
                    parent = this,
                    number = number,
                    traveledNodes = traveledNodes
                )

            else -> right!!.insert(number, traveledNodes)
        }
    }

    private fun Node.insertLeft(number: Number, traveledNodes: MutableSet<NodeId>) {
        when (left) {
            null ->
                left = buildNode(
                    parent = this,
                    number = number,
                    traveledNodes = traveledNodes
                )

            else -> left!!.insert(number, traveledNodes)
        }
    }

    private fun buildNode(
        parent: Node,
        number: Number,
        traveledNodes: Set<NodeId>,
    ): Node {
        return Node(
            value = number,
            id = NodeId(UUID.randomUUID().toString()),
            parent = parent,
            insertSide = if (number.toDouble() < parent.value.toDouble()) Left else Right,
        ).also {
            onNodeInserted(
                node = it,
                traveledNodes = traveledNodes,
                rootInsertSide = if (number.toDouble() < root.value.toDouble()) Left else Right,
            )
        }
    }

    abstract fun onRootInserted(node: Node)

    abstract fun onNodeInserted(
        node: Node,
        rootInsertSide: InsertSide,
        traveledNodes: Set<NodeId>
    )
}

private infix fun Number.isLessThen(other: Number): Boolean = this.toDouble() < other.toDouble()


data class Node(
    val value: Number,
    val id: NodeId,
    val parent: Node?,
    val insertSide: InsertSide?,
    var left: Node? = null,
    var right: Node? = null,
)
