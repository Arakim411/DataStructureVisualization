package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel.BinarySearchTree
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel.LinkedList
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

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
    DataStructureTypeUiModel.HashMap,
    LinkedList
)

internal fun DataStructureType.toUiModel(): DataStructureTypeUiModel = when (this) {
    DataStructureType.BinarySearchTree -> DataStructureTypeUiModel.BinarySearchTree
    DataStructureType.HashMap -> DataStructureTypeUiModel.HashMap
    DataStructureType.LinkedList -> DataStructureTypeUiModel.LinkedList

}
