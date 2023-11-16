package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource

import com.arakim.datastructurevisualization.domain.util.executeCommonIoAction
import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.database.DataStructureDao
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.DataStructureDto
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.toDomain
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.toDto
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStructureLocalDataSource @Inject constructor(
    private val dao: DataStructureDao,
) {

    suspend fun createDataStructure(
        name: String,
        dataSourceType: DataStructureType,
    ): TypedResult<Unit, CommonError> = executeCommonIoAction {
        dao.createDataStructure(
            DataStructureDto(
                name = name,
                dataSourceType = dataSourceType.toDto(),
                dataStructureJson = "",

                ),
        )
    }

    fun listenForDataStructuresUpdate(): Flow<TypedResult<List<DataStructure>, CommonError>> =
        dao.listenForDataStructuresUpdate().map {
            executeCommonIoAction {
                it.toDomain()
            }
        }
}