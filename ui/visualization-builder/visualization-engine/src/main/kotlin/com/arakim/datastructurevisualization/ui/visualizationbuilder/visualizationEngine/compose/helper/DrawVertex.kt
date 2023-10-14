package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.helper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.uiModel.DrawStyle
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElement
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.VisualizationElementShape

internal fun DrawScope.drawVisualizationElement(
    element: VisualizationElement,
    textMeasurer: TextMeasurer,
    center: Offset,
    drawStyle: DrawStyle,
) {
    when (element.shape) {
        VisualizationElementShape.Circle -> drawElementCircleShape(
            center = center,
            drawStyle = drawStyle,
        )

        VisualizationElementShape.Square -> drawElementSquareShape(
            center = center,
            drawStyle = drawStyle,
        )
    }

    drawElementTitle(
        title = element.title,
        textMeasurer = textMeasurer,
        center = center,
        drawStyle = drawStyle,
    )

}

private fun DrawScope.drawElementCircleShape(
    center: Offset,
    drawStyle: DrawStyle,
) {
    drawCircle(
        color = drawStyle.colors.elementBackground,
        radius = drawStyle.sizes.circleRadius,
        center = center,
    )

    drawCircle(
        color = drawStyle.colors.elementLineColor,
        radius = drawStyle.sizes.circleRadius,
        center = center,
        style = Stroke(drawStyle.sizes.elementStroke),
    )
}

private fun DrawScope.drawElementSquareShape(
    center: Offset,
    drawStyle: DrawStyle,
) {
    val edgeSize = drawStyle.sizes.squareEdgeSize
    val halfEdge = drawStyle.sizes.squareEdgeSize / 2

    val topLeft = Offset(center.x - halfEdge, center.y - halfEdge)

    drawRect(
        topLeft = topLeft,
        color = drawStyle.colors.elementBackground,
        size = Size(edgeSize, edgeSize)
    )

    drawRect(
        topLeft = topLeft,
        color = drawStyle.colors.elementLineColor,
        style = Stroke(drawStyle.sizes.elementStroke),
        size = Size(edgeSize, edgeSize)
    )

}

private fun DrawScope.drawElementTitle(
    title: String,
    textMeasurer: TextMeasurer,
    center: Offset,
    drawStyle: DrawStyle,
) {
    val measure = textMeasurer.measure(
        text = title,
        style = TextStyle.Default.copy(fontSize = drawStyle.sizes.textSize),
    )

    val textTopLeft = Offset(
        x = center.x - measure.size.width / 2,
        y = center.y - measure.size.height / 2,
    )

    drawText(measure, topLeft = textTopLeft, color = drawStyle.colors.textColor)
}
