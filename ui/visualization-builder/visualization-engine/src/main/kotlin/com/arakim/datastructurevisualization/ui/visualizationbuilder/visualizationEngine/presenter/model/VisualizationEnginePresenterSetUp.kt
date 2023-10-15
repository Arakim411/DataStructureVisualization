package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class VisualizationEnginePresenterSetUp(
    val vertexTransitionTime: Duration,
    val comparisonTransitionTime: Duration,
)

val DefaultVisualizationEnginePresenterSetUp = VisualizationEnginePresenterSetUp(
    vertexTransitionTime = 1.seconds,
    comparisonTransitionTime = 1.seconds,
)