package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose.helper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.TextUnit
import com.arakim.datastructurevisualization.ui.visualizationbuilder.setUpPicker.presenter.model.DrawColorsUiModel
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VisualizationElement
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.presenter.model.vertex.VisualizationElementShape

internal fun DrawScope.drawVisualizationElement(
    element: VisualizationElement,
    textMeasurer: TextMeasurer,
    center: Offset,
    colors: DrawColorsUiModel,
    circleRadius: Float,
    elementStroke: Float,
    squareEdgeSize: Float,
    fontSize: TextUnit,
) {
    when (element.shape) {
        VisualizationElementShape.Circle -> drawElementCircleShape(
            center = center,
            colors = colors,
            circleRadius = circleRadius,
            elementStroke = elementStroke,

            )

        VisualizationElementShape.Square -> drawElementSquareShape(
            center = center,
            colors = colors,
            squareEdgeSize = squareEdgeSize,
            elementStroke = elementStroke,

            )
    }

    if (element.shotTitle) {
        drawElementTitle(
            title = element.title,
            colors = colors,
            textMeasurer = textMeasurer,
            center = center,
            fontSize = fontSize,

        )
    }

}

private fun DrawScope.drawElementCircleShape(
    center: Offset,
    colors: DrawColorsUiModel,
    circleRadius: Float,
    elementStroke: Float,
) {
    drawCircle(
        color = colors.elementBackground,
        radius = circleRadius,
        center = center,
    )

    drawCircle(
        color = colors.elementShapeColor,
        radius = circleRadius,
        center = center,
        style = Stroke(elementStroke),
    )
}

private fun DrawScope.drawElementSquareShape(
    center: Offset,
    colors: DrawColorsUiModel,
    squareEdgeSize: Float,
    elementStroke: Float,
) {
    val halfEdge = squareEdgeSize / 2

    val topLeft = Offset(center.x - halfEdge, center.y - halfEdge)

    drawRect(
        topLeft = topLeft,
        color = colors.elementBackground,
        size = Size(squareEdgeSize, squareEdgeSize)
    )

    drawRect(
        topLeft = topLeft,
        color = colors.elementShapeColor,
        style = Stroke(elementStroke),
        size = Size(squareEdgeSize, squareEdgeSize)
    )
}

fun DrawScope.drawElementTitle(
    title: String,
    textMeasurer: TextMeasurer,
    center: Offset,
    fontSize: TextUnit,
    colors: DrawColorsUiModel,
) {
    val measure = textMeasurer.measure(
        text = title,
        style = TextStyle.Default.copy(fontSize = fontSize),
    )

    val textTopLeft = Offset(
        x = center.x - measure.size.width / 2,
        y = center.y - measure.size.height / 2,
    )

    drawText(measure, topLeft = textTopLeft, color = colors.textColor)
}
