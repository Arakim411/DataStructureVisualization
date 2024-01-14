package com.arakim.datastrucutrevisualization.domain.dataStructures.useCases

import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import javax.inject.Inject

class StopDeletionProcessUseCase @Inject constructor(
    private val dataStructureRepository: DataStructureRepository
) {
    suspend operator fun invoke(id: Int) = dataStructureRepository.stopDeletionProcess(id)
}
