package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.InsertSide
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.InsertSide.Left
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.InsertSide.Right
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.model.NodeId
import com.arakim.datastructurevisualization.ui.util.mapToImmutable
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance.BelowOnLeft
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance.BelowOnRight
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationSetUp
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class BinarySearchTreeVisualizationBuilder @Inject constructor(
    val visualizationBuilder: VisualizationBuilder,
    private val branchAlignHelper: BranchAlignHelper,
) : BinarySearchTree() {

    lateinit var setUp: VisualizationSetUp

    private val belowDistance by lazy { (setUp.drawConfig.sizes.circleRadius * 3f) }
    private val horizontalDistance by lazy { (setUp.drawConfig.sizes.circleRadius * 3f) }

    fun initialize(setUp: VisualizationSetUp) {
        this.setUp = setUp
        visualizationBuilder.initialize(setUp)
        branchAlignHelper.initialize(
            visualizationBuilder = visualizationBuilder,
            horizontalAlignDistance = horizontalDistance,
        )
    }

    override fun onRootInserted(node: Node) {
        visualizationBuilder.createVertexWithEnterTransition(
            vertexId = node.toVertexId(),
            //TODO should be at middle of screen, compose needs to initialize it
            position = VertexPosition.CoordinatesPosition(DpOffset(200.dp, 100.dp)),
            title = node.title(),
        )
    }

    override fun onNodeInserted(
        node: Node,
        rootInsertSide: InsertSide,
        traveledNodes: Set<NodeId>,
    ) {
        visualizationBuilder.createVertexWithEnterTransition(
            vertexId = node.toVertexId(),
            position = VertexPosition.RelativePosition(
                relativeVertexId = node.parent!!.toVertexId(),
                relativePositionDistance = node.insertSide!!.getRelativePosition()
            ),
            title = node.title(),
            comparisons = traveledNodes.mapToImmutable { it.toVertexId() }
        )
        visualizationBuilder.createConnection(
            from = node.parent.toVertexId(),
            to = node.toVertexId(),
        )

        branchAlignHelper.alignTreeIfNeeded(node, rootInsertSide)
    }

    private fun InsertSide.getRelativePosition(): RelativePositionDistance = when (this) {
        Left -> BelowOnLeft(belowDistance = belowDistance, leftDistance = horizontalDistance)
        Right -> BelowOnRight(belowDistance = belowDistance, rightDistance = horizontalDistance)
    }
}

fun Node.toVertexId(): VertexId = id.toVertexId()
fun NodeId.toVertexId(): VertexId = VertexId(value)

fun Node.title(): String {
    val valueFloat = value.toFloat()
    val valueInt = value.toInt()

    return if (valueFloat > valueInt)
        valueFloat.toString() else
        valueInt.toString()
}