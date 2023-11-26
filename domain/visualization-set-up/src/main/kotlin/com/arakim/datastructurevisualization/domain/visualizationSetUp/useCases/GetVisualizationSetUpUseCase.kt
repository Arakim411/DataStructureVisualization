package com.arakim.datastructurevisualization.domain.visualizationSetUp.useCases

import com.arakim.datastructurevisualization.domain.visualizationSetUp.VisualizationSetUpRepository
import javax.inject.Inject

class GetVisualizationSetUpUseCase @Inject constructor(
    private val visualizationSetUpRepository: VisualizationSetUpRepository
) {

    suspend operator fun invoke(id: Int) = visualizationSetUpRepository.getVisualizationSetUp(id)
}
