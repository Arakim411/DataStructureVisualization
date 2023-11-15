package com.arakim.datastrucutrevisualization.domain.dataStructures

import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType

interface DataStructureRepository {

    fun create(name: String, type: DataStructureType)
}
