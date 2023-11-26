package com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.mappers

import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.DrawColorsDto
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.DrawConfigDto
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.DrawSizesDto
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.OffsetDto
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.VisualizationSetUpDto
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.VisualizationSetUpEntity
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.DrawColors
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.DrawConfig
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.DrawSizes
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.VisualizationSetUp
import kotlin.time.Duration.Companion.milliseconds

internal fun VisualizationSetUpEntity.toDomain() = VisualizationSetUp(
    id = id,
    vertexTransitionTime = visualizationSetUpDto.vertexTransitionMillis.milliseconds,
    comparisonTransitionTime = visualizationSetUpDto.comparisonTransitionMillis.milliseconds,
    enterTransStartPositionDp = visualizationSetUpDto.enterTransStartPositionDp.toPair(),
    drawConfig = visualizationSetUpDto.drawConfig.toDomain()
)

private fun DrawConfigDto.toDomain() = DrawConfig(
    elementStartPositionDp = elementStartPositionDp.toPair(),
    drawColors = colors.toDomain(),
    drawSizes = drawSizes.toDomain()
)

private fun DrawColorsDto.toDomain() = DrawColors(
    elementBackground = elementBackground,
    comparisonShapeColor = comparisonShapeColor,
    elementShapeColor = elementShapeColor,
    connectionLineColor = connectionLineColor,
    connectionArrowColor = connectionArrowColor,
    textColor = textColor,
)

private fun DrawSizesDto.toDomain() = DrawSizes(
    circleRadiusDp = circleRadiusDp,
    squareEdgeSizeDp = squareEdgeSizeDp,
    elementStrokeDp = elementStrokeDp,
    lineStrokeDp = lineStrokeDp,
    textSizeDp = textSizeDp,
    arrowSizeDp = arrowSizeDp
)

private fun OffsetDto.toPair() = Pair<Int, Int>(xDp, yDp)