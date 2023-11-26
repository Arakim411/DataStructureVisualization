package com.arakim.datastructurevisualization.data.repository.visualizationSetUp

import com.arakim.datastructurevisualization.data.visualizationSetUp.localDataSource.VisualizationSetUpLocalDataSource
import com.arakim.datastructurevisualization.domain.visualizationSetUp.VisualizationSetUpRepository
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.VisualizationSetUp
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.VisualizationSetUpDefaults
import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class VisualizationSetUpRepositoryImpl @Inject constructor(
    private val localDataSource: VisualizationSetUpLocalDataSource,
) : VisualizationSetUpRepository {

    override suspend fun createDefaultVisualizationSetUp(id: Int): TypedResult<Unit, CommonError> {
        VisualizationSetUpDefaults.apply {
            val defaultVisualizationSetUp = VisualizationSetUp(
                id = id,
                vertexTransitionTime = vertexTransitionTime,
                comparisonTransitionTime = comparisonTransitionTime,
                enterTransStartPositionDp = enterTransStartPositionDp,
                drawConfig = drawConfig,
            )

            return localDataSource.insertVisualizationSetUp(defaultVisualizationSetUp)
        }
    }

    override suspend fun updateVisualizationSetUp(setUp: VisualizationSetUp): TypedResult<Unit, CommonError> {
        return localDataSource.updateVisualizationSetUp(setUp)
    }

    override suspend fun getVisualizationSetUp(id: Int): TypedResult<VisualizationSetUp?, CommonError> {
        return localDataSource.getVisualizationSetUp(id)
    }

    override fun listenForVisualizationSetUpUpdates(
        id: Int,
    ): Flow<TypedResult<VisualizationSetUp, CommonError>> {
        return localDataSource.listenForVisualizationSetUpUpdates(id)
    }


}