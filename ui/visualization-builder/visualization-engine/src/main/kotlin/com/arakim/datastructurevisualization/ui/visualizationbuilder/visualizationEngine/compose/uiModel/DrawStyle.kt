package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.uiModel

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//TODO name
@Immutable
data class DrawStyle(
    val elementStartPosition: DpOffset,
    val colors: DrawColors,
    val sizes: DrawSizes,
) {
    companion object {
        val Default = DrawStyle(
            elementStartPosition = DpOffset(0.dp, 0.dp),
            colors = ColorsDefault,
            sizes = SizesDefault,
        )
    }
}

@Immutable
data class DrawColors(
    val elementBackground: Color,
    val animShapeColor: Color, //TODO NAME
    val elementLineColor: Color,
    val connectionLineColor: Color,
    val connectionArrowColor: Color,
    val textColor: Color,
)

@Immutable
data class DrawSizes(
    val circleRadius: Float,
    val squareEdgeSize: Float,
    val elementStroke: Float,
    val lineStroke: Float,
    val textSize: TextUnit,
    val arrowSize: Float
)

private val ColorsDefault =
    DrawColors(
        elementBackground = Color.Green,
        animShapeColor = Color.Red,
        elementLineColor = Color.Black,
        connectionLineColor = Color.DarkGray,
        connectionArrowColor = Color.DarkGray,
        textColor = Color.Black,
    )

private val SizesDefault =
    DrawSizes(
        circleRadius = 50f,
        elementStroke = 4f,
        lineStroke = 3f,
        squareEdgeSize = 80f,
        textSize = 12.sp,
        arrowSize = 30f
    )