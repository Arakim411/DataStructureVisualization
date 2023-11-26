package com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model

import androidx.room.TypeConverter
import com.google.gson.Gson

data class VisualizationSetUpDto(
    val vertexTransitionMillis: Long,
    val comparisonTransitionMillis: Long,
    val enterTransStartPositionDp: OffsetDto,
    val drawConfig: DrawConfigDto
)

class VisualizationSetUpTypeConverter {

    @TypeConverter
    fun fromJson(json: String): VisualizationSetUpDto {
        return Gson().fromJson(json, VisualizationSetUpDto::class.java)
    }

    @TypeConverter
    fun toJson(visualizationSetUp: VisualizationSetUpDto): String = Gson().toJson(visualizationSetUp)

}