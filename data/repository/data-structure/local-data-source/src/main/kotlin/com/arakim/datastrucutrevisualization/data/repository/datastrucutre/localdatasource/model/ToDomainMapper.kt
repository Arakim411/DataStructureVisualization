package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model

import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure

internal fun List<DataStructureEntity>.toDomain(): List<DataStructure> = map { it.toDomain() }

internal fun DataStructureEntity.toDomain(): DataStructure = DataStructure(
    id = id,
    name = name,
    type = dataSourceType.toDataStructureType(),
    dataStructureJson = dataStructureJson,
    isFavorite = isFavorite,
)
