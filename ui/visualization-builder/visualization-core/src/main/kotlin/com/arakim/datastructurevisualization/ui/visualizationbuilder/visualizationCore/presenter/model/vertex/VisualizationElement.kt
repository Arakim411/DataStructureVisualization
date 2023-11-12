package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpOffset

data class VisualizationElement(
    val title: String,
    val position: Animatable<DpOffset, AnimationVector2D>,
    var finalPosition: DpOffset,
    var shotTitle: Boolean,
    val shape: VisualizationElementShape,
    var isVisible: Boolean,
    var showIncomingConnections: Boolean,
)

@Immutable
enum class VisualizationElementShape {
    Circle,
    Square,
}