package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

data class PresenterSetUp(
    val vertexTransitionTime: Duration,
    val comparisonTransitionTime: Duration,
)

val DefaultPresenterSetUp = PresenterSetUp(
    vertexTransitionTime = 1.seconds,
    comparisonTransitionTime = 1.seconds,
)