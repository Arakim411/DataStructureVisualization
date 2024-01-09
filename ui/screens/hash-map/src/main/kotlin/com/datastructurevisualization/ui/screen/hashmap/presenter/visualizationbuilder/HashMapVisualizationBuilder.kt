package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder

import com.arakim.datastructurevisualization.kotlinutil.DataStructureSerializer
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers.HashMapSerializer
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class HashMapVisualizationBuilder @Inject constructor(
    private val hashMapSerializer: HashMapSerializer,
) : DataStructureSerializer by hashMapSerializer {

    fun initialize() {

    }
}