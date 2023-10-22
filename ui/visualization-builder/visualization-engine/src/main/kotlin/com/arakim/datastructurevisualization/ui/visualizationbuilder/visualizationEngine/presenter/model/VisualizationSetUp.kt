package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model

import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class VisualizationSetUp(
    val vertexTransitionTime: Duration,
    val comparisonTransitionTime: Duration,
    val enterTransStartPosition: DpOffset,
    val drawConfig: DrawConfig,
)

val DefaultVisualizationSetUp = VisualizationSetUp(
    vertexTransitionTime = 1.seconds,
    comparisonTransitionTime = 1.seconds,
    enterTransStartPosition = DpOffset(50.dp, 50.dp),
    drawConfig = DrawConfig.Default,
)