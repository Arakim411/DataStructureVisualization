package com.arakim.datastrucutrevisualization.domain.dataStructures.useCases

import com.arakim.datastructurevisualization.kotlinutil.getOrNull
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import javax.inject.Inject

class SaveDataStructureUseCase @Inject constructor(
    private val dataStructureRepository: DataStructureRepository
) {

    suspend operator fun invoke(id: Int, dataStructureJson: String) {
        val dataStructure = dataStructureRepository.getDataStructure(id).getOrNull() ?: return
        dataStructureRepository.updateDataStructure(dataStructure.copy(dataStructureJson = dataStructureJson))
    }
}
