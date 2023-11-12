package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.helpers.align.TreeAlignHelper
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.BinarySearchTree
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.BinarySearchTreeListener
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.childrenCount
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.getInsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.Node
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.model.NodeId
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.toVertexId
import com.arakim.datastructurevisualization.ui.util.mapToImmutable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers.transitions.getComparisonsPositions
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VisualizationElementShape.Circle
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class VisualizeNodeDeletedHelper @Inject constructor(
    private val visualizationBuilder: VisualizationBuilder,
    private val treeAlignHelper: TreeAlignHelper,
) : BinarySearchTreeListener {

    private lateinit var binarySearchTree: BinarySearchTree
    private var elementHorizontalDistance: Dp = 0.dp
    private var elementVerticalDistance: Dp = 0.dp

    fun initialize(
        binarySearchTree: BinarySearchTree,
        elementHorizontalDistance: Dp,
        elementVerticalDistance: Dp,
    ) {
        this.binarySearchTree = binarySearchTree
        this.elementHorizontalDistance = elementHorizontalDistance
        this.elementVerticalDistance = elementVerticalDistance
    }

    override fun on0ChildNodeDeleted(
        node: Node,
        traveledNodes: Set<NodeId>,
        rootInsertSide: InsertSide?,
    ) {
        visualizationBuilder.addTransitionHelper.removeVertexTransition(
            vertexId = node.toVertexId(),
            comparisons = traveledNodes.mapToImmutable { it.toVertexId() },
            invokeAfter = {
                treeAlignHelper.alignNodeDeletion.align0ChildNodeDeleted(
                    node = node,
                    rootInsertSide = rootInsertSide,
                )
            },
        )
    }

    //TODO refactor
    override fun on1ChildNodeDeleted(
        node: Node,
        traveledNodes: Set<NodeId>,
        newConnection: Node,
        rootInsertSide: InsertSide?,
    ) {
        val deletedNodePosition = visualizationBuilder.visualizationCore.getFinalPosition(node.toVertexId())!!

        visualizationBuilder.addTransitionHelper.removeVertexTransition(
            vertexId = node.toVertexId(),
            comparisons = traveledNodes.mapToImmutable { it.toVertexId() },
            invokeAfter = {
                treeAlignHelper.alignNodeDeletion.align1ChildNodeDeleted(
                    node = node,
                    newConnection = newConnection,
                    rootInsertSide = rootInsertSide,
                    deletedNodePosition = deletedNodePosition,
                )
            },
        )
    }

    override fun on2ChildNodeDeleted(
        replacedNode: Node,
        traveledNodes: Set<NodeId>,
        replacement: Node,
    ) {

        visualizationBuilder.addTransitionHelper.addActionTransition(
            action = { corePresenter ->
                corePresenter.apply {
                    with(visualizationBuilder.addTransitionHelper.handleComparisons) {
                        invoke(
                            comparisons = getComparisonsPositions(traveledNodes.mapToImmutable { it.toVertexId() }),
                            shape = Circle
                        )
                    }
                }
            },
        )
        visualizationBuilder.addTransitionHelper.moveTextTransition(
            from = replacement.toVertexId(),
            to = replacedNode.toVertexId(),
            invokeAfter = {
                when (replacement.childrenCount()) {
                    0 -> on0ChildNodeDeleted(
                        node = replacement,
                        traveledNodes = setOf(),
                        rootInsertSide = binarySearchTree.root!!.getInsertSide(replacement.value),
                    )

                    1 -> on1ChildNodeDeleted(
                        node = replacement,
                        traveledNodes = setOf(),
                        newConnection = replacement.right!!,
                        rootInsertSide = binarySearchTree.root!!.getInsertSide(replacement.value),
                    )

                    else -> throw IllegalStateException("replacement should have 0 or 1 child")
                }
            }
        )
    }
}