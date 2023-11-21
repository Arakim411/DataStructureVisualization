package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.Const
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.DataStructureDto
import kotlinx.coroutines.flow.Flow

@Dao
interface DataStructureDao {

    @Insert
    suspend fun createDataStructure(dataStructureEntity: DataStructureDto)

    @Query("SELECT * FROM ${Const.DataStructureEntityName}")
    fun listenForDataStructuresUpdate(): Flow<List<DataStructureDto>>

    @Query("DELETE FROM ${Const.DataStructureEntityName} WHERE id = :id")
    fun deleteDataStructure(id: Int)
}