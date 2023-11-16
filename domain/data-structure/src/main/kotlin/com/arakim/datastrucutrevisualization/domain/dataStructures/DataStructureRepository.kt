package com.arakim.datastrucutrevisualization.domain.dataStructures

import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import kotlinx.coroutines.flow.Flow

interface DataStructureRepository {

    suspend fun createDataStructure(name: String, type: DataStructureType): TypedResult<Unit, CommonError>
    fun listenForDataStructuresUpdate(): Flow<TypedResult<List<DataStructure>, CommonError>>
}
