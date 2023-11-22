package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.align

import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AlignNodeInsertion @Inject constructor(
    visualizationBuilder: VisualizationBuilder,
) : TreeMoveHelper(visualizationBuilder) {

    fun alignTreeAfterInsertion(insertedNode: Node, rootInsertSide: InsertSide) {
        val distance = if (rootInsertSide == Left) -horizontalAlignDistance else horizontalAlignDistance
        alignTreeHorizontally(
            node = insertedNode,
            rootInsertSide = rootInsertSide,
            distance = distance,
        )
    }

}