package com.arakim.datastrucutrevisualization.domain.dataStructures.useCases

import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import javax.inject.Inject

class CreateDataStructureUseCase @Inject constructor(
    private val repository: DataStructureRepository
) {
    suspend operator fun invoke(
        name: String,
        type: DataStructureType
    ) = repository.createDataStructure(name, type)
}
