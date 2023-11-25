package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.Const
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.DataStructureDto
import kotlinx.coroutines.flow.Flow

@Dao
interface DataStructureDao {

    @Insert
    suspend fun createDataStructure(dataStructureEntity: DataStructureDto)

    @Query("SELECT * FROM ${Const.DataStructureEntityName}")
    fun listenForDataStructuresUpdate(): Flow<List<DataStructureDto>>

    @Query("SELECT * FROM ${Const.DataStructureEntityName} WHERE id = :id")
    suspend fun getDataStructure(id: Int): DataStructureDto?

    @Update
    suspend fun updateDataStructure(dataStructure: DataStructureDto)

    @Query("DELETE FROM ${Const.DataStructureEntityName} WHERE id = :id")
    fun deleteDataStructure(id: Int)
}