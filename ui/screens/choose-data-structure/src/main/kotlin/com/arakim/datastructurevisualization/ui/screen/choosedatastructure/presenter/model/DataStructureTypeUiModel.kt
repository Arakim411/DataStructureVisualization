package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType

@Immutable
enum class DataStructureTypeUiModel(name: String, @DrawableRes iconResId: Int) {

    BinarySearchTree("Binary Search Tree", R.drawable.ic_binary_search_tree),
    HashMap("HashMap", R.drawable.ic_binary_search_tree),
    LinkedList("Linked List", R.drawable.ic_binary_search_tree),
}

internal fun DataStructureType.toUiModel(): DataStructureTypeUiModel = when (this) {
    DataStructureType.BinarySearchTree -> DataStructureTypeUiModel.BinarySearchTree
    DataStructureType.HashMap -> DataStructureTypeUiModel.HashMap
    DataStructureType.LinkedList -> DataStructureTypeUiModel.LinkedList

}