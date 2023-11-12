package com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose.helper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotateRad
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationCore.compose.model.DrawConfigUiModel

internal fun DrawScope.drawConnection(from: Offset, to: Offset, drawConfig: DrawConfigUiModel) {
    val angelRadForStartCircle = StrictMath.atan2(to.y.toDouble() - from.y, to.x.toDouble() - from.x)
    val angelRadForEndCircle = StrictMath.atan2(from.y.toDouble() - to.y, from.x.toDouble() - to.x)

    val lineStartOffset = getPointOnCircle(
        radius = drawConfig.sizes.circleRadius,
        angelRad = angelRadForStartCircle,
        center = from,
    )

    val lineEndOffset = getPointOnCircle(
        radius = drawConfig.sizes.circleRadius * 1.8f,
        angelRad = angelRadForEndCircle,
        center = to,
    )

    drawConnectionLine(
        from = lineStartOffset,
        to = lineEndOffset,
        drawConfig = drawConfig
    )

    drawConnectionArrow(
        offset = lineEndOffset,
        angelRad = angelRadForEndCircle.toFloat(),
        drawConfig = drawConfig,
    )

}

private fun DrawScope.drawConnectionLine(
    from: Offset,
    to: Offset,
    drawConfig: DrawConfigUiModel,
) = Path().apply {

    moveTo(from.x, from.y)
    lineTo(to.x, to.y)

    drawPath(
        path = this,
        color = drawConfig.colors.connectionLineColor,
        style = Stroke(
            width = drawConfig.sizes.lineStroke,
        ),
    )
}

private fun DrawScope.drawConnectionArrow(offset: Offset, angelRad: Float, drawConfig: DrawConfigUiModel) =
    Path().apply {
        val size = drawConfig.sizes.arrowSize
        val halfSize = size / 2
        rotateRad(
            radians = angelRad - degrees90InRad,
            pivot = offset,
        ) {

            moveTo(offset.x - halfSize, offset.y)
            lineTo(offset.x + halfSize, offset.y)
            lineTo(offset.x, offset.y - size)
            close()


            drawPath(
                path = this@apply,
                color = drawConfig.colors.connectionArrowColor,
            )
        }
    }

private val degrees90InRad = Math.toRadians(90.0).toFloat()