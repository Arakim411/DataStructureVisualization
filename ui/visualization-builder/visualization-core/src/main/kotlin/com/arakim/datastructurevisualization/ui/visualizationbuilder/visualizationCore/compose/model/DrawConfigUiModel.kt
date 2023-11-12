package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.DrawColors
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.DrawConfig
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.DrawSizes

@Immutable
data class DrawConfigUiModel(
    val elementStartPosition: DpOffset,
    val colors: DrawColors,
    val sizes: DrawSizesUiModel,
)

@Immutable
data class DrawSizesUiModel(
    val circleRadius: Float,
    val squareEdgeSize: Float,
    val elementStroke: Float,
    val lineStroke: Float,
    val textSize: TextUnit,
    val arrowSize: Float
)

fun DrawConfig.toUiModel(density: Density): DrawConfigUiModel = DrawConfigUiModel(
    elementStartPosition = elementStartPosition,
    colors = colors,
    sizes = sizes.toDrawSizesUiModel(density)
)

fun DrawSizes.toDrawSizesUiModel(density: Density): DrawSizesUiModel = with(density){
    DrawSizesUiModel(
        circleRadius = circleRadius.toPx(),
        squareEdgeSize = squareEdgeSize.toPx(),
        elementStroke = elementStroke.toPx(),
        lineStroke = lineStroke.toPx(),
        textSize = textSize,
        arrowSize = arrowSize.toPx()
    )
}