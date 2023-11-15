package com.arakim.datastructurevisualization.data.repository.dataStructure

import com.arakim.datastructurevisualization.data.repository.dataStructure.localDataSource.LocalDataStructureSource
import com.arakim.datastrucutrevisualization.domain.dataStructures.DataStructureRepository
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import javax.inject.Inject

internal class DataStructureRepositoryImpl @Inject constructor(
    private val localDataStructureSource: LocalDataStructureSource,
) : DataStructureRepository {

    override fun create(name: String, type: DataStructureType) {
        localDataStructureSource.create(name, type)
    }
}