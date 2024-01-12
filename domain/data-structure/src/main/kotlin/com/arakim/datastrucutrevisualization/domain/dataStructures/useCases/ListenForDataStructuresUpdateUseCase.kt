package com.arakim.datastrucutrevisualization.domain.dataStructures.useCases

import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastructurevisualization.kotlinutil.map
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import kotlinx.coroutines.flow.map
import javax.inject.Inject

typealias DsResult = TypedResult<List<DataStructure>, CommonError>

class ListenForDataStructuresUpdateUseCase @Inject constructor(
    private val dataStructureRepository: DataStructureRepository
) {

    operator fun invoke() = dataStructureRepository
        .listenForDataStructuresUpdate()
        .map { result ->
            result.filterNotDeleted()
        }

    private fun TypedResult<List<DataStructure>, CommonError>.filterNotDeleted(): DsResult =
        map { dataStructures ->
            dataStructures.filter { it.deletionDateUtc == null }
        }
}
