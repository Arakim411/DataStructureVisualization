package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import javax.inject.Inject

class BranchAlignHelper @Inject constructor() {

    private lateinit var visualizationBuilder: VisualizationBuilder
    private var horizontalAlignDistance: Dp? = null

    fun initialize(
        visualizationBuilder: VisualizationBuilder,
        horizontalAlignDistance: Dp
    ) {
        this.visualizationBuilder = visualizationBuilder
        this.horizontalAlignDistance = horizontalAlignDistance
    }

    fun alignTreeIfNeeded(insertedNode: Node, rootInsertSide: InsertSide) {
        when {
            rootInsertSide == Left && insertedNode.insertSide == Right -> insertedNode.moveBranchToLeft()
            rootInsertSide == Right && insertedNode.insertSide == Left -> insertedNode.moveBranchToRight()
        }
    }

    private fun Node.moveBranchToLeft() {
        val first = parent?.firstInsertedOnLeft() ?: return
        visualizationBuilder.moveVertexWithConnections(
            vertexId = first.toVertexId(),
            transition = DpOffset(-horizontalAlignDistance!!, 0.dp)
        )
    }

    private fun Node.firstInsertedOnLeft(): Node? =
        when (insertSide) {
            Left -> this
            Right -> parent?.firstInsertedOnLeft()
            null -> null
        }

    private fun Node.moveBranchToRight() {
        val first = parent?.firstInsertedOnRight() ?: return
        visualizationBuilder.moveVertexWithConnections(
            vertexId = first.toVertexId(),
            transition = DpOffset(horizontalAlignDistance!!, 0.dp)
        )
    }

    private fun Node.firstInsertedOnRight(): Node? =
        when (insertSide) {
            Right -> this
            Left -> parent?.firstInsertedOnRight()
            null -> null
        }


}