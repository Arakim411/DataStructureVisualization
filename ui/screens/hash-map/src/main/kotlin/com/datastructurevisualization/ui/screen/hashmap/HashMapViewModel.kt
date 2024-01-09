package com.datastructurevisualization.ui.screen.hashmap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HashMapViewModel @Inject constructor(
     val presenter: HashMapPresenter,
) : ViewModel() {

    init {
        presenter.initialize(viewModelScope)
    }

}