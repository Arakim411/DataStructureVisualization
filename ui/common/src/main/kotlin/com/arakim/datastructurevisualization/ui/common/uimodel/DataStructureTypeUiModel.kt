package com.arakim.datastructurevisualization.ui.common.uimodel

import androidx.compose.runtime.Immutable
import com.arakim.datastructurevisualization.ui.common.R
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.BinarySearchTree
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.HashMap
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.LinkedList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType

//TODO correct icons
@Immutable
sealed interface DataStructureTypeUiModel {
    val name: String
    val iconResId: Int


    @Immutable
    object BinarySearchTree : DataStructureTypeUiModel {
        override val name: String = "Binary Search Tree"
        override val iconResId: Int = R.drawable.ic_binary_search_tree
    }

    @Immutable
    object HashMap : DataStructureTypeUiModel {
        override val name: String = "Hash Map"
        override val iconResId: Int = R.drawable.ic_hash_map
    }

    @Immutable
    object LinkedList : DataStructureTypeUiModel {
        override val name: String = "Linked List"
        override val iconResId: Int = R.drawable.ic_linked_list
    }
}

val allDataStructuresTypeUiModels = immutableListOf(
    BinarySearchTree,
    HashMap,
)

internal fun DataStructureType.toUiModel(): DataStructureTypeUiModel = when (this) {
    DataStructureType.BinarySearchTree -> BinarySearchTree
    DataStructureType.HashMap -> HashMap
    DataStructureType.LinkedList -> LinkedList

}
