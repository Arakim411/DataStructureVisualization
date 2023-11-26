package com.arakim.datastructurevisualization.domain.visualizationSetUp.useCases

import com.arakim.datastructurevisualization.domain.visualizationSetUp.VisualizationSetUpRepository
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.VisualizationSetUp
import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenForVisualizationSetUpUpdatesUseCase @Inject constructor(
    private val visualizationSetUpRepository: VisualizationSetUpRepository
) {
    operator fun invoke(id: Int): Flow<TypedResult<VisualizationSetUp, CommonError>> {
        return visualizationSetUpRepository.listenForVisualizationSetUpUpdates(id)
    }
}
