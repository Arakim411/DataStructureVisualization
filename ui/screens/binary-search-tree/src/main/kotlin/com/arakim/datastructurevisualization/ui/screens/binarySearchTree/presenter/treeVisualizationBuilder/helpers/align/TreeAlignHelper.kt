package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.align

import androidx.compose.ui.unit.Dp
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.BinarySearchTree
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TreeAlignHelper @Inject constructor(
    val alignNodeInsertion: AlignNodeInsertion,
    val alignNodeDeletion: AlignNodeDeletion,
) {
    fun initialize(
        binarySearchTree: BinarySearchTree,
        horizontalAlignDistance: Dp
    ) {

        alignNodeInsertion.initialize(
            binarySearchTree = binarySearchTree,
            horizontalAlignDistance = horizontalAlignDistance
        )

        alignNodeDeletion.initialize(
            binarySearchTree = binarySearchTree,
            horizontalAlignDistance = horizontalAlignDistance
        )
    }


}
