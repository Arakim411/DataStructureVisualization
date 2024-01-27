package com.arakim.datastructurevisualization.testdatagenerator

import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType

fun fakeDataStructures(count: Int = 10) = (0..count).map { fakeDataStructure() }

fun fakeDataStructure() = DataStructure(
    id = randomInt(),
    name = randomString(),
    type = randomType(),
    dataStructureJson = randomString(),
    isFavorite = randomBoolean(),
    deletionDateUtc = null
)

fun randomType(): DataStructureType = DataStructureType.values().random()
