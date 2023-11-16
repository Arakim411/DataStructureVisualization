package com.arakim.datastrucutrevisualization.domain.dataStructures.useCases

import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import javax.inject.Inject

class ListenForDataStructuresUpdateUseCase @Inject constructor(
    private val dataStructureRepository: DataStructureRepository
) {

    operator fun invoke() = dataStructureRepository.listenForDataStructuresUpdate()
}
