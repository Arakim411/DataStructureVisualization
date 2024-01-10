package com.arakim.datastructurevisualization.domain.visualizationSetUp.model

data class DrawConfig(
    val elementStartPositionDp: Pair<Int, Int>,
    val drawColors: DrawColors,
    val drawSizes: DrawSizes
)

data class DrawColors(
    val elementBackground: Long,
    val comparisonShapeColor: Long,
    val elementShapeColor: Long,
    val connectionLineColor: Long,
    val connectionArrowColor: Long,
    val textColor: Long
)

data class DrawSizes(
    val circleRadiusDp: Float,
    val squareEdgeSizeDp: Float,
    val elementStrokeDp: Float,
    val lineStrokeDp: Float,
    val textSizeDp: Float,
    val arrowSizeDp: Float
)

private const val Red = 0xFFFF0000
private const val Green = 0xFF00FF00
private const val Black = 0xFF000000
private const val Black50 = 0x80000000
private const val DarkGray = 0xFF444444

val DefaultDrawColors = DrawColors(
    elementBackground = Green,
    comparisonShapeColor = Red,
    elementShapeColor = Black50,
    connectionLineColor = DarkGray,
    connectionArrowColor = DarkGray,
    textColor = Black
)

val DefaultDrawSizes = DrawSizes(
    circleRadiusDp = 15f,
    squareEdgeSizeDp = 30f,
    elementStrokeDp = 1.5f,
    lineStrokeDp = 1f,
    textSizeDp = 12f,
    arrowSizeDp = 7f
)

val DefaultDrawConfig = DrawConfig(
    elementStartPositionDp = 0 to 0,
    drawColors = DefaultDrawColors,
    drawSizes = DefaultDrawSizes
)
