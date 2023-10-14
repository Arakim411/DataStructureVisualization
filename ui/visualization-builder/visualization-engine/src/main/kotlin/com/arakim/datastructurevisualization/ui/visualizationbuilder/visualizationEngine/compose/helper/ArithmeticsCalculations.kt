package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.helper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import kotlin.math.cos
import kotlin.math.sin

internal fun getPointOnCircle(
    radius: Float,
    angelRad: Double,
    center: Offset,
): Offset {
    val x = radius * cos(angelRad) + center.x
    val y = radius * sin(angelRad) + center.y
    return Offset(x.toFloat(), y.toFloat())
}

internal fun DpOffset.toOffset(density: Density): Offset = with(density) {
    Offset(x.toPx(), y.toPx())
}
