package com.arakim.datastructurevisualization.domain.visualizationSetUp.useCases

import com.arakim.datastructurevisualization.domain.visualizationSetUp.VisualizationSetUpRepository
import com.arakim.datastructurevisualization.domain.visualizationSetUp.model.VisualizationSetUp
import javax.inject.Inject

class UpdateVisualizationSetUpUseCase @Inject constructor(
    private val visualizationSetUpRepository: VisualizationSetUpRepository
) {

    suspend operator fun invoke(
        setUp: VisualizationSetUp
    ) = visualizationSetUpRepository.updateVisualizationSetUp(setUp)
}
