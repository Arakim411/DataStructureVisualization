package com.arakim.datastrucutrevisualization.domain.dataStructures.useCases

import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureId
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import javax.inject.Inject

class CreateDataStructureUseCase @Inject constructor(
    private val dataStructureRepository: DataStructureRepository
) {
    suspend operator fun invoke(
        name: String,
        type: DataStructureType
    ): TypedResult<DataStructureId, CommonError> = dataStructureRepository.createDataStructure(name, type)
}
