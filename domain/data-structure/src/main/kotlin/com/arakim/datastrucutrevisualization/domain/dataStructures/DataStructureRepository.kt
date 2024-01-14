package com.arakim.datastrucutrevisualization.domain.dataStructures

import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import kotlinx.coroutines.flow.Flow

typealias DataStructureId = Int

interface DataStructureRepository {

    suspend fun createDataStructure(
        name: String,
        type: DataStructureType
    ): TypedResult<DataStructureId, CommonError>

    suspend fun updateDataStructure(dataStructure: DataStructure): TypedResult<Unit, CommonError>
    suspend fun getDataStructure(id: Int): TypedResult<DataStructure, CommonError>

    suspend fun deleteDataStructure(id: Int): TypedResult<Unit, CommonError>
    suspend fun startDataStructureDeletionProcess(id: Int): TypedResult<Unit, CommonError>
    suspend fun stopDeletionProcess(id: Int): TypedResult<Unit, CommonError>
    fun listenForDataStructuresUpdate(): Flow<TypedResult<List<DataStructure>, CommonError>>
}
