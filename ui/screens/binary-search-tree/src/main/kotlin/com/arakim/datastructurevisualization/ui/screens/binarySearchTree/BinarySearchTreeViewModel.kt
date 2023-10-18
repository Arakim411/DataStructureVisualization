package com.arakim.datastructurevisualization.ui.screens.binarySearchTree

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreePresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@Stable
class BinarySearchTreeViewModel @Inject constructor(
    val presenter: BinarySearchTreePresenter,
) : ViewModel() {

    init {
        presenter.initialize(viewModelScope)
    }
}