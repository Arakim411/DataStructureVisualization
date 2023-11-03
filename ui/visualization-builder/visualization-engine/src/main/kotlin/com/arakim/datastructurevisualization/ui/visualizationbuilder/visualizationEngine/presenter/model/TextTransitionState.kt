package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.ui.unit.DpOffset

sealed interface TextTransitionState {
    object IdleState : TextTransitionState
    data class MovingState(
        val text: String,
        val position: Animatable<DpOffset, AnimationVector2D>
    ): TextTransitionState
}