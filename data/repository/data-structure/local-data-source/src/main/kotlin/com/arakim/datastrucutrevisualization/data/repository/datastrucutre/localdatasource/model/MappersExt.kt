package com.arakim.datastrucutrevisualization.data.repository.datastrucutre.localdatasource.model

import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType.BinarySearchTree
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType.HashMap
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType.LinkedList

internal fun List<DataStructureDto>.toDomain(): List<DataStructure> = map { it.toDomain() }

internal fun DataStructureDto.toDomain(): DataStructure = DataStructure(
    id = id,
    name = name,
    type = dataSourceType.toDataStructureType(),
    dataStructureJson = dataStructureJson,
)

internal fun DataStructureType.toDto(): String = when (this) {
    BinarySearchTree -> DataStructuresKey.BinarySearchTree
    HashMap -> DataStructuresKey.HashMap
    LinkedList -> DataStructuresKey.LinkedList
}

internal fun String.toDataStructureType(): DataStructureType = when (this) {
    DataStructuresKey.BinarySearchTree -> BinarySearchTree
    DataStructuresKey.HashMap -> HashMap
    DataStructuresKey.LinkedList -> LinkedList
    else -> throw IllegalArgumentException("Unknown data structure type: $this")

}

internal object DataStructuresKey {
    const val BinarySearchTree = "BinarySearchTreeKey"
    const val HashMap = "HashMapKey"
    const val LinkedList = "LinkedListKey"
}
