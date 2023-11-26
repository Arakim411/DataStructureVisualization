package com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource

import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.mappers.toDomain
import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.model.mappers.toEntity
import com.arakim.datastructurevisualization.domain.util.executeCommonIoAction
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.VisualizationSetUp
import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VisualizationSetUpLocalDataSource @Inject constructor(
    private val dao: VisualizationSetUpDao
) {
    suspend fun insertVisualizationSetUp(
        setUp: VisualizationSetUp
    ): TypedResult<Unit, CommonError> =
        executeCommonIoAction {
            withContext(Dispatchers.IO) {
                dao.insertVisualizationSetUp(setUp.toEntity())
            }
        }

    suspend fun updateVisualizationSetUp(
        setUp: VisualizationSetUp
    ): TypedResult<Unit, CommonError> =
        executeCommonIoAction {
            withContext(Dispatchers.IO) {
                dao.updateVisualizationSetUp(setUp.toEntity())
            }
        }

    suspend fun getVisualizationSetUp(id: Int): TypedResult<VisualizationSetUp?, CommonError> =
        executeCommonIoAction {
            withContext(Dispatchers.IO) {
                dao.getVisualizationSetUp(id)?.toDomain()
            }
        }

    fun listenForVisualizationSetUpUpdates(id: Int) = dao.listenForVisualizationSetUpUpdate(id)
        .filterNotNull()
        .map {
            executeCommonIoAction {
                it.toDomain()
            }
        }

}