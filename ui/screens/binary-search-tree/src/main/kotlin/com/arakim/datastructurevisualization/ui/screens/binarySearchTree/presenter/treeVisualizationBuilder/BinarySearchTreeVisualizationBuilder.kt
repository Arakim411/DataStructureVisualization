package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder

import android.util.Log
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.VisualizeNodeDeletedHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.VisualizeNodeInsertedHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.align.TreeAlignHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.BinarySearchTree
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.helpers.TreeHelpers
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationSetUp
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import javax.inject.Inject

@ViewModelScoped
class BinarySearchTreeVisualizationBuilder @Inject constructor(
    val visualizationBuilder: VisualizationBuilder,
    private val treeAlignHelper: TreeAlignHelper,
    private val visualizeNodeInsertedHelper: VisualizeNodeInsertedHelper,
    private val visualizeNodeDeletedHelper: VisualizeNodeDeletedHelper,
    treeHelpers: TreeHelpers,
) : BinarySearchTree(
    handleNodeDeletion = treeHelpers.handleTreeNodeDeletionHelper
) {

    private lateinit var setUp: VisualizationSetUp

    private val elementVerticalDistance by lazy { (setUp.drawConfig.sizes.circleRadius * 3f) }
    private val elementHorizontalDistance by lazy { (setUp.drawConfig.sizes.circleRadius * 3f) }

    fun initialize(setUp: VisualizationSetUp) {
        this.setUp = setUp
        visualizationBuilder.initialize(setUp)
        treeAlignHelper.initialize(
            binarySearchTree = this,
            horizontalAlignDistance = elementHorizontalDistance,
        )
        visualizeNodeInsertedHelper.initialize(
            elementHorizontalDistance = elementHorizontalDistance,
            elementVerticalDistance = elementVerticalDistance,
        )
        visualizeNodeDeletedHelper.initialize(
            binarySearchTree = this,
            elementHorizontalDistance = elementHorizontalDistance,
            elementVerticalDistance = elementVerticalDistance,
        )
        addListener(visualizeNodeInsertedHelper)
        addListener(visualizeNodeDeletedHelper)
    }

    override fun insert(number: Number) {
        visualizationBuilder.addTransitionHelper.addActionTransition {
            super.insert(number)
        }
    }

    override fun delete(number: Number) {
        visualizationBuilder.addTransitionHelper.addActionTransition {
            super.delete(number)
        }
    }
}
