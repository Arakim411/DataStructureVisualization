package com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.Const

@Entity(tableName = Const.VisualizationSetUpEntityName)
data class VisualizationSetUpEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "visualization_set_up_json") val visualizationSetUpDto: VisualizationSetUpDto,
)


