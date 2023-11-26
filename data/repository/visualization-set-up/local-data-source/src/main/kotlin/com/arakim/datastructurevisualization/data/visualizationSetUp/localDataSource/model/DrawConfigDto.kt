package com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class DrawConfigDto(
    @ColumnInfo("element_start_position_dp") val elementStartPositionDp: OffsetDto,
    @ColumnInfo("colors") val colors: DrawColorsDto,
    @ColumnInfo("draw_sizes") val drawSizes: DrawSizesDto
)


@Entity
data class DrawColorsDto(
    @ColumnInfo("element_background") val elementBackground: Long,
    @ColumnInfo("comparison_shape_color") val comparisonShapeColor: Long,
    @ColumnInfo("element_shape_color") val elementShapeColor: Long,
    @ColumnInfo("connection_line_color") val connectionLineColor: Long,
    @ColumnInfo("connection_arrow_color") val connectionArrowColor: Long,
    @ColumnInfo("text_color") val textColor: Long
)


@Entity
data class DrawSizesDto(
    @ColumnInfo("circle_radius_dp") val circleRadiusDp: Float,
    @ColumnInfo("square_edge_size_dp") val squareEdgeSizeDp: Float,
    @ColumnInfo("element_stroke_dp") val elementStrokeDp: Float,
    @ColumnInfo("line_stroke_dp") val lineStrokeDp: Float,
    @ColumnInfo("text_size_dp") val textSizeDp: Float,
    @ColumnInfo("arrow_size_dp") val arrowSizeDp: Float
)