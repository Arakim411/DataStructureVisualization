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

internal fun VisualizationSetUp.toEntity() = VisualizationSetUpEntity(
    id = id, visualizationSetUpDto = VisualizationSetUpDto(
        vertexTransitionMillis = vertexTransitionTime.inWholeMilliseconds,
        comparisonTransitionMillis = comparisonTransitionTime.inWholeMilliseconds,
        enterTransStartPositionDp = OffsetDto(
            enterTransStartPositionDp.first,
            enterTransStartPositionDp.second
        ),
        drawConfig = drawConfig.toDto()
    )
)

private fun DrawConfig.toDto() = DrawConfigDto(
    elementStartPositionDp = OffsetDto(elementStartPositionDp.first, elementStartPositionDp.second),
    colors = drawColors.toDto(),
    drawSizes = drawSizes.toDto()
)

private fun DrawColors.toDto() = DrawColorsDto(
    elementBackground = elementBackground,
    comparisonShapeColor = comparisonShapeColor,
    elementShapeColor = elementShapeColor,
    connectionLineColor = connectionLineColor,
    connectionArrowColor = connectionArrowColor,
    textColor = textColor,
)

private fun DrawSizes.toDto() = DrawSizesDto(
    circleRadiusDp = circleRadiusDp,
    squareEdgeSizeDp = squareEdgeSizeDp,
    elementStrokeDp = elementStrokeDp,
    lineStrokeDp = lineStrokeDp,
    textSizeDp = textSizeDp,
    arrowSizeDp = arrowSizeDp
)