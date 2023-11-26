package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.DataStructureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DataStructureDao {

    @Insert
    suspend fun createDataStructure(dataStructureEntity: DataStructureEntity) : Long

    @Query("SELECT * FROM ${Const.DataStructureEntityName}")
    fun listenForDataStructuresUpdate(): Flow<List<DataStructureEntity>>

    @Query("SELECT * FROM ${Const.DataStructureEntityName} WHERE id = :id")
    suspend fun getDataStructure(id: Int): DataStructureEntity?

    @Update
    suspend fun updateDataStructure(dataStructure: DataStructureEntity)

    @Query("DELETE FROM ${Const.DataStructureEntityName} WHERE id = :id")
    fun deleteDataStructure(id: Int)
}