package com.arakim.datastrucutrevisualization.domain.dataStructures.useCases

import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import javax.inject.Inject

class GetDataStructureByIdUseCase @Inject constructor(
    private val dataStructureRepository: DataStructureRepository
) {

    suspend operator fun invoke(
        id: Int
    ): TypedResult<DataStructure, CommonError> = dataStructureRepository.getDataStructure(id)
}
