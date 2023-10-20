package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.ui.unit.DpOffset

sealed interface ComparisonState {

    object IdleState : ComparisonState
    data class ComparingState(
        val position: Animatable<DpOffset, AnimationVector2D>,
        val shape: VisualizationElementShape,
    ) : ComparisonState

}