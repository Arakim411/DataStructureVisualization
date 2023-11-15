package com.arakim.datastructurevisualization.ui.screen.choosedatastructure

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructurePresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseDataStructureViewModel @Inject constructor(
    val presenter: ChooseDataStructurePresenter
): ViewModel() {

    init {
        presenter.initialize(viewModelScope)
    }
}