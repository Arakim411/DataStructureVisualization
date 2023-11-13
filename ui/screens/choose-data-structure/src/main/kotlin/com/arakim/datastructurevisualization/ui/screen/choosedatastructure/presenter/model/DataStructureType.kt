package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R

@Immutable
enum class DataStructureType(name: String, @DrawableRes iconResId: Int) {

    BinarySearchTree("Binary Search Tree", R.drawable.ic_binary_search_tree),
}