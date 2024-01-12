package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeletedDataStructureViewModel @Inject constructor(
    val presenter: DeletedDataStructuresPresenter,
) : ViewModel() {

    init {
        presenter.initialize(viewModelScope)
    }

}