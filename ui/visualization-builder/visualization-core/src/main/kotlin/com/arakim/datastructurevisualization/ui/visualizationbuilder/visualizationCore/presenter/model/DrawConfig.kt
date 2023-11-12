package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class DrawConfig(
    val elementStartPosition: DpOffset,
    val colors: DrawColors,
    val sizes: DrawSizes,
) {
    companion object {
        val Default = DrawConfig(
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
    val circleRadius: Dp,
    val squareEdgeSize: Dp,
    val elementStroke: Dp,
    val lineStroke: Dp,
    val textSize: TextUnit,
    val arrowSize: Dp
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
        circleRadius = 15.dp,
        elementStroke = 1.dp,
        lineStroke = 1.5.dp,
        squareEdgeSize = 10.dp,
        textSize = 12.sp,
        arrowSize = 7.dp,
    )