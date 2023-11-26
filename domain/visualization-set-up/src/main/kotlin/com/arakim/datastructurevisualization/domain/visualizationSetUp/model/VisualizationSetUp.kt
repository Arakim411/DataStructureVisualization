package com.arakim.datastructurevisualization.domain.visualizationSetUp.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class VisualizationSetUp(
    val id: Int,
    val vertexTransitionTime: Duration,
    val comparisonTransitionTime: Duration,
    val enterTransStartPositionDp: Pair<Int, Int>,
    val drawConfig: DrawConfig
)

object VisualizationSetUpDefaults {
    val vertexTransitionTime = 0.5.seconds
    val comparisonTransitionTime = 0.5.seconds
    val enterTransStartPositionDp = 50 to 50
    val drawConfig = DefaultDrawConfig
}
