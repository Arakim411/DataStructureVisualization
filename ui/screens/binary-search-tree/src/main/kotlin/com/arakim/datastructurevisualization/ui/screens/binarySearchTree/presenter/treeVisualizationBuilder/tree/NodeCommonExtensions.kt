package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree

import android.util.Log
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId

internal fun Node.toVertexId(): VertexId = id.toVertexId()
internal fun NodeId.toVertexId(): VertexId = VertexId(value)

internal fun Node.title(): String {
    val valueFloat = value.toFloat()
    val valueInt = value.toInt()

    return if (valueFloat > valueInt)
        valueFloat.toString() else
        valueInt.toString()
}

internal infix fun Number.isLessThen(other: Number): Boolean = this.toDouble() < other.toDouble()
internal infix fun Number.isEquals(other: Number): Boolean = this.toDouble() == other.toDouble()
internal fun Node.getInsertSide(number: Number): InsertSide = when {
    number isLessThen value -> Left
    else -> Right
}

internal fun Node.childrenCount(): Int = when {
    left != null && right != null -> 2
    left != null || right != null -> 1
    else -> 0
}

internal fun Node.print(tag: String) {
    Log.d(tag, "node: ${value.toInt()} L: ${left?.value?.toInt()} R: ${right?.value?.toInt()}")
    left?.print(tag)
    right?.print(tag)
}
