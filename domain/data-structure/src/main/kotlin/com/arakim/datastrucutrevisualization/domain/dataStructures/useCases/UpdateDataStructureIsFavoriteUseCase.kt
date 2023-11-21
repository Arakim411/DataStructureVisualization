package com.arakim.datastrucutrevisualization.domain.dataStructures.useCases

import com.arakim.datastructurevisualization.kotlinutil.getOrNull
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import javax.inject.Inject

class UpdateDataStructureIsFavoriteUseCase @Inject constructor(
    private val repository: DataStructureRepository
) {
    suspend operator fun invoke(id: Int, isFavorite: Boolean) {
        val dataStructure = repository.getDataStructure(id).getOrNull() ?: return
        val result = repository.updateDataStructure(dataStructure.copy(isFavorite = isFavorite))
    }
}
