package com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.model

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.graph.VertexId


sealed interface VertexPosition {

    data class CoordinatesPosition(val offset: DpOffset) : VertexPosition

    data class RelativePosition(
        val relativeVertexId: VertexId,
        val relativePositionDistance: RelativePositionDistance,
    ) : VertexPosition

}

sealed interface RelativePositionDistance {

    data class Above(val distance: Dp) : RelativePositionDistance
    data class Below(val distance: Dp) : RelativePositionDistance
    data class OnRight(val distance: Dp) : RelativePositionDistance
    data class OnLeft(val distance: Dp) : RelativePositionDistance

    data class BelowOnLeft(val belowDistance: Dp, val leftDistance: Dp) : RelativePositionDistance
    data class BelowOnRight(val belowDistance: Dp, val rightDistance: Dp) : RelativePositionDistance
    data class AboveOnLeft(val aboveDistance: Dp, val leftDistance: Dp) : RelativePositionDistance
    data class AboveOnRight(val aboveDistance: Dp, val rightDistance: Dp) : RelativePositionDistance

}