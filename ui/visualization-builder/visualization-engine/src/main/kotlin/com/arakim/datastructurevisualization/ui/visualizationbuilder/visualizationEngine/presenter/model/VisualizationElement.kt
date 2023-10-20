package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.DpOffset

data class VisualizationElement(
    val title: String,
    val position: Animatable<DpOffset, AnimationVector2D>,
    val finalPosition: DpOffset,
    val shape: VisualizationElementShape,
    var isVisible: Boolean,
)

@Immutable
enum class VisualizationElementShape {
    Circle,
    Square,
}