package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder

import com.arakim.datastructurevisualization.kotlinutil.DataStructureSerializer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.VisualizeNodeDeletedHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.VisualizeNodeInsertedHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.align.TreeAlignHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.BinarySearchTree
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.helpers.BinarySearchTreeSerializer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.helpers.TreeHelpers
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.VisualizationSetUp
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@ViewModelScoped
class BinarySearchTreeVisualizationBuilder @Inject constructor(
    val visualizationBuilder: VisualizationBuilder,
    private val treeAlignHelper: TreeAlignHelper,
    private val visualizeNodeInsertedHelper: VisualizeNodeInsertedHelper,
    private val visualizeNodeDeletedHelper: VisualizeNodeDeletedHelper,
    private val binarySearchTreeSerializer: BinarySearchTreeSerializer,
    treeHelpers: TreeHelpers,
) : BinarySearchTree(
    handleNodeDeletion = treeHelpers.handleTreeNodeDeletionHelper,
), DataStructureSerializer by binarySearchTreeSerializer {

    private lateinit var setUp: VisualizationSetUp

    init {
        visualizationBuilder.setOnVisualizationSetUpChanged {
            setUp = it
        }
        binarySearchTreeSerializer.initialize(this)
    }

    private val elementVerticalDistance by lazy { (setUp.drawConfig.sizes.circleRadius * 3f) }
    private val elementHorizontalDistance by lazy { (setUp.drawConfig.sizes.circleRadius * 3f) }

    fun initialize(
        binarySearchTreeJson: String? = null,
        coroutineScope: CoroutineScope,
        onInitialized: () -> Unit,
    ) {
        // TODO handle binarySearchTreeJson
        visualizationBuilder.initialize(
            coroutineScope = coroutineScope,
            onInitialized = {
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
                onInitialized()
            },
        )
        if (!binarySearchTreeJson.isNullOrEmpty()) {
            createFromJson(binarySearchTreeJson)
        }

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
