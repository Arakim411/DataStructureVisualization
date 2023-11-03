package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.helper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.compose.model.DrawConfigUiModel
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElement
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.vertex.VisualizationElementShape

internal fun DrawScope.drawVisualizationElement(
    element: VisualizationElement,
    textMeasurer: TextMeasurer,
    center: Offset,
    drawConfig: DrawConfigUiModel,
) {
    when (element.shape) {
        VisualizationElementShape.Circle -> drawElementCircleShape(
            center = center,
            drawConfig = drawConfig,
        )

        VisualizationElementShape.Square -> drawElementSquareShape(
            center = center,
            drawConfig = drawConfig,
        )
    }

    if (element.shotTitle) {
        drawElementTitle(
            title = element.title,
            textMeasurer = textMeasurer,
            center = center,
            drawConfig = drawConfig,
        )
    }

}

private fun DrawScope.drawElementCircleShape(
    center: Offset,
    drawConfig: DrawConfigUiModel,
) {
    drawCircle(
        color = drawConfig.colors.elementBackground,
        radius = drawConfig.sizes.circleRadius,
        center = center,
    )

    drawCircle(
        color = drawConfig.colors.elementLineColor,
        radius = drawConfig.sizes.circleRadius,
        center = center,
        style = Stroke(drawConfig.sizes.elementStroke),
    )
}

private fun DrawScope.drawElementSquareShape(
    center: Offset,
    drawConfig: DrawConfigUiModel,
) {
    val edgeSize = drawConfig.sizes.squareEdgeSize
    val halfEdge = drawConfig.sizes.squareEdgeSize / 2

    val topLeft = Offset(center.x - halfEdge, center.y - halfEdge)

    drawRect(
        topLeft = topLeft,
        color = drawConfig.colors.elementBackground,
        size = Size(edgeSize, edgeSize)
    )

    drawRect(
        topLeft = topLeft,
        color = drawConfig.colors.elementLineColor,
        style = Stroke(drawConfig.sizes.elementStroke),
        size = Size(edgeSize, edgeSize)
    )
}

 fun DrawScope.drawElementTitle(
    title: String,
    textMeasurer: TextMeasurer,
    center: Offset,
    drawConfig: DrawConfigUiModel,
) {
    val measure = textMeasurer.measure(
        text = title,
        style = TextStyle.Default.copy(fontSize = drawConfig.sizes.textSize),
    )

    val textTopLeft = Offset(
        x = center.x - measure.size.width / 2,
        y = center.y - measure.size.height / 2,
    )

    drawText(measure, topLeft = textTopLeft, color = drawConfig.colors.textColor)
}
