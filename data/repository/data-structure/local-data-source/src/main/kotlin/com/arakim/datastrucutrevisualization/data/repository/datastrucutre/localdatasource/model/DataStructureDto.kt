package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.Const

@Entity(tableName = Const.DataStructureEntityName)
data class DataStructureDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "data_source_type") val dataSourceType: String,
    @ColumnInfo(name = "data_structure_Json") val dataStructureJson: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
)