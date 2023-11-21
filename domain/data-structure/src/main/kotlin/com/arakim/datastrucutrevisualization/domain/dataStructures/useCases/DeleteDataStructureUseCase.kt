package com.arakim.datastrucutrevisualization.domain.dataStructures.useCases

import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import javax.inject.Inject

class DeleteDataStructureUseCase @Inject constructor(
    private val dataStructureRepository: DataStructureRepository
) {
    suspend operator fun invoke(id: Int): TypedResult<Unit, CommonError> =
        dataStructureRepository.deleteDataStructure(id)
}
