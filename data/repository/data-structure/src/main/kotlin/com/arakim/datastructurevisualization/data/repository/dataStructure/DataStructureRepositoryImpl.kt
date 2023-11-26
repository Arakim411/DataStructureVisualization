package com.arakim.datastructurevisualization.data.repository.dataStructure

import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.DataStructureLocalDataSource
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureId
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DataStructureRepositoryImpl @Inject constructor(
    private val localDataSource: DataStructureLocalDataSource,
) : DataStructureRepository {

    override suspend fun createDataStructure(
        name: String,
        type: DataStructureType,
    ): TypedResult<DataStructureId, CommonError> = localDataSource.createDataStructure(
        name = name,
        dataSourceType = type,
    )

    override suspend fun updateDataStructure(dataStructure: DataStructure): TypedResult<Unit, CommonError> =
        localDataSource.updateDataStructure(dataStructure)


    override suspend fun getDataStructure(id: Int): TypedResult<DataStructure, CommonError> =
        localDataSource.getDataStructure(id)

    override suspend fun deleteDataStructure(id: Int): TypedResult<Unit, CommonError> {
        // TODO not delete instantly, but mark as deleted and delete after some time, to allows revert it
        return localDataSource.deleteDataStructure(id)
    }

    override fun listenForDataStructuresUpdate(
    ): Flow<TypedResult<List<DataStructure>, CommonError>> = localDataSource.listenForDataStructuresUpdate()

}