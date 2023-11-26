package com.arakim.datastructurevisualization.domain.visualizationSetUp

import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.VisualizationSetUp
import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import kotlinx.coroutines.flow.Flow

interface VisualizationSetUpRepository {

    suspend fun createDefaultVisualizationSetUp(id: Int): TypedResult<Unit, CommonError>
    suspend fun updateVisualizationSetUp(setUp: VisualizationSetUp): TypedResult<Unit, CommonError>
    suspend fun getVisualizationSetUp(id: Int): TypedResult<VisualizationSetUp?, CommonError>

    fun listenForVisualizationSetUpUpdates(id: Int): Flow<TypedResult<VisualizationSetUp, CommonError>>
}
