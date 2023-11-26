package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource

import com.arakim.datastructurevisualization.domain.util.executeCommonIoAction
import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.DataStructureEntity
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.toDomain
import com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model.toDto
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureId
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataStructureLocalDataSource @Inject constructor(
    private val dao: DataStructureDao,
) {

    suspend fun createDataStructure(
        name: String,
        dataSourceType: DataStructureType,
    ): TypedResult<DataStructureId, CommonError> = executeCommonIoAction {
        val id = dao.createDataStructure(
            DataStructureEntity(
                name = name,
                dataSourceType = dataSourceType.toDto(),
                dataStructureJson = "",
                isFavorite = false,
            ),
        )
        id.toInt()
    }

    suspend fun getDataStructure(id: Int): TypedResult<DataStructure, CommonError> = executeCommonIoAction {
        requireNotNull(dao.getDataStructure(id)?.toDomain())
    }


    suspend fun updateDataStructure(
        dataStructure: DataStructure,
    ): TypedResult<Unit, CommonError> = executeCommonIoAction {
        withContext(Dispatchers.IO) {
            val dto = dataStructure.toDto()
            dao.updateDataStructure(dto)
        }
    }

    suspend fun deleteDataStructure(
        id: Int,
    ): TypedResult<Unit, CommonError> = executeCommonIoAction {
        withContext(Dispatchers.IO) {
            dao.deleteDataStructure(id)
        }
    }


    fun listenForDataStructuresUpdate(): Flow<TypedResult<List<DataStructure>, CommonError>> =
        dao.listenForDataStructuresUpdate().map {
            executeCommonIoAction {
                it.toDomain()
            }
        }
}