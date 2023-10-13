package com.arakim.datastructurevisualization.ui.visualizationEngine.compose.helper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.arakim.datastructurevisualization.ui.visualizationEngine.compose.uiModel.DrawStyle
import com.arakim.datastructurevisualization.ui.visualizationEngine.presenter.graph.Vertex

internal fun DrawScope.drawVertex(
    vertex: Vertex,
    textMeasurer: TextMeasurer,
    position: Offset,
    drawStyle: DrawStyle,
) {

    val measure = textMeasurer.measure(
        text = vertex.element.title,
        style = TextStyle.Default.copy(fontSize = drawStyle.sizes.textSize),
    )
    val x = position.x - measure.size.width / 2
    val y = position.y - measure.size.height / 2
    val textTopLeft = Offset(x, y)

    drawCircle(
        color = drawStyle.colors.elementBackground,
        radius = drawStyle.sizes.circleRadius,
        center = position,
    )

    drawCircle(
        color = drawStyle.colors.elementLineColor,
        radius = drawStyle.sizes.circleRadius,
        center = position,
        style = Stroke(drawStyle.sizes.elementStroke),
    )

    drawText(measure, topLeft = textTopLeft, color = drawStyle.colors.textColor)

}
