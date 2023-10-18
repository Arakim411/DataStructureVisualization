package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers

import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.Side.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.Side.None
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.Side.Right

abstract class BinarySearchTree {

    lateinit var root: Node

    fun insert(number: Number) {
        if (::root.isInitialized) {
            root.insert(number)
        } else {
            root = buildNode(previous = null, number = number, None)
        }
    }

    private fun Node.insert(number: Number) {
        if (number isLessThen value) {
            insertLeft(number)
        } else {
            insertRight(number)
        }
    }


    private fun Node.insertRight(number: Number) {
        when (right) {
            null ->
                right = buildNode(previous = value, number = number, Right)

            else -> right!!.insert(number)
        }
    }

    private fun Node.insertLeft(number: Number) {
        when (left) {
            null ->
                left = buildNode(previous = value, number = number, Left)

            else -> left!!.insert(number)
        }
    }

    fun buildNode(previous: Number?, number: Number, side: Side): Node {
        onInserted(previous, number, side)
        return Node(number)
    }

    abstract fun onInserted(previous: Number?, inserted: Number, side: Side)

}

enum class Side {
    Left,
    Right,
    None
}

private infix fun Number.isLessThen(other: Number): Boolean = this.toDouble() < other.toDouble()


data class Node(val value: Number, var left: Node? = null, var right: Node? = null)
