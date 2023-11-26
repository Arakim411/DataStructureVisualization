package com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.DrawColors
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.DrawConfig
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.DrawSizes

@Immutable
data class DrawConfigUiModel(
    val elementStartPosition: DpOffset,
    val colors: DrawColorsUiModel,
    val sizes: DrawSizesUiModelDp,
)

@Immutable
data class DrawColorsUiModel(
    val elementBackground: Color,
    val comparisonShapeColor: Color,
    val elementShapeColor: Color,
    val connectionLineColor: Color,
    val connectionArrowColor: Color,
    val textColor: Color,
)

@Immutable
data class DrawSizesUiModelDp(
    val circleRadius: Dp,
    val squareEdgeSize: Dp,
    val elementStroke: Dp,
    val lineStroke: Dp,
    val textSize: TextUnit,
    val arrowSize: Dp
)

@Immutable
data class DrawSizeUiModelPx(
    val circleRadius: Float,
    val squareEdgeSize: Float,
    val elementStroke: Float,
    val lineStroke: Float,
    val textSize: TextUnit,
    val arrowSize: Float
)

@Stable
fun DrawSizesUiModelDp.toDrawSizesPx(density: Density) = with(density) {
    DrawSizeUiModelPx(
        circleRadius = circleRadius.toPx(),
        squareEdgeSize = squareEdgeSize.toPx(),
        elementStroke = elementStroke.toPx(),
        lineStroke = lineStroke.toPx(),
        textSize = textSize,
        arrowSize = arrowSize.toPx(),
    )
}

fun DrawConfig.toUiModel(): DrawConfigUiModel {
    return DrawConfigUiModel(
        elementStartPosition = elementStartPositionDp.toDpOffset(),
        colors = drawColors.toUiModel(),
        sizes = drawSizes.toUiModelDp(),
    )
}

fun DrawColors.toUiModel(): DrawColorsUiModel {
    return DrawColorsUiModel(
        elementBackground = Color(elementBackground),
        comparisonShapeColor = Color(comparisonShapeColor),
        elementShapeColor = Color(elementShapeColor),
        connectionLineColor = Color(connectionLineColor),
        connectionArrowColor = Color(connectionArrowColor),
        textColor = Color(textColor),
    )
}

fun DrawSizes.toUiModelDp(): DrawSizesUiModelDp {
    return DrawSizesUiModelDp(
        circleRadius = circleRadiusDp.dp,
        squareEdgeSize = squareEdgeSizeDp.dp,
        elementStroke = elementStrokeDp.dp,
        lineStroke = lineStrokeDp.dp,
        textSize = textSizeDp.sp,
        arrowSize = arrowSizeDp.dp,
    )
}

fun DrawConfigUiModel.toDomain(): DrawConfig = DrawConfig(
    elementStartPositionDp = elementStartPosition.toPair(),
    drawColors = colors.toDomain(),
    drawSizes = sizes.toDomain(),
)

fun DrawColorsUiModel.toDomain(): DrawColors = DrawColors(
    elementBackground = elementBackground.toArgb().toLong(),
    comparisonShapeColor = comparisonShapeColor.toArgb().toLong(),
    elementShapeColor = elementShapeColor.toArgb().toLong(),
    connectionLineColor = connectionLineColor.toArgb().toLong(),
    connectionArrowColor = connectionArrowColor.toArgb().toLong(),
    textColor = textColor.toArgb().toLong(),
)

fun DrawSizesUiModelDp.toDomain(): DrawSizes = DrawSizes(
    circleRadiusDp = circleRadius.value,
    squareEdgeSizeDp = squareEdgeSize.value,
    elementStrokeDp = elementStroke.value,
    lineStrokeDp = lineStroke.value,
    textSizeDp = textSize.value,
    arrowSizeDp = arrowSize.value,
)