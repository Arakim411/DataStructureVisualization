package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex

import androidx.compose.ui.unit.DpOffset

sealed interface VertexMoveType {
    val dpOffset: DpOffset

    data class MoveTo(override val dpOffset: DpOffset) : VertexMoveType
    data class MoveBy(override val dpOffset: DpOffset) : VertexMoveType
}