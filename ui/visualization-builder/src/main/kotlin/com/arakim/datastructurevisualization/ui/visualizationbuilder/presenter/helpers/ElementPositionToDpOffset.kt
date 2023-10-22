package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.helpers

import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.RelativePositionDistance
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model.VertexPosition
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.Vertex
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.graph.VertexId

fun VertexPosition.toDpOffset(
    getVertex: (VertexId) -> Vertex,
): DpOffset = when (this) {
    is VertexPosition.CoordinatesPosition -> offset
    is VertexPosition.RelativePosition -> referencePositionToDpOffset(getVertex(relativeVertexId))
}

private fun VertexPosition.RelativePosition.referencePositionToDpOffset(
    relativeVertex: Vertex,
): DpOffset {

    val relativePosition = relativeVertex.element.finalPosition

    return relativePosition.let {
        when (relativePositionDistance) {
            is RelativePositionDistance.Above -> DpOffset(it.x, it.y - relativePositionDistance.distance)
            is RelativePositionDistance.Below -> DpOffset(it.x, it.y + relativePositionDistance.distance)
            is RelativePositionDistance.OnRight -> DpOffset(it.x + relativePositionDistance.distance, it.y)
            is RelativePositionDistance.OnLeft -> DpOffset(it.x - relativePositionDistance.distance, it.y)
            is RelativePositionDistance.BelowOnLeft ->
                DpOffset(
                    it.x - relativePositionDistance.leftDistance,
                    it.y + relativePositionDistance.belowDistance,
                )

            is RelativePositionDistance.BelowOnRight ->
                DpOffset(
                    it.x + relativePositionDistance.rightDistance,
                    it.y + relativePositionDistance.belowDistance,
                )

            is RelativePositionDistance.AboveOnLeft -> DpOffset(
                it.x - relativePositionDistance.leftDistance,
                it.y - relativePositionDistance.aboveDistance
            )

            is RelativePositionDistance.AboveOnRight -> DpOffset(
                it.x + relativePositionDistance.rightDistance,
                it.y - relativePositionDistance.aboveDistance
            )
        }
    }
}