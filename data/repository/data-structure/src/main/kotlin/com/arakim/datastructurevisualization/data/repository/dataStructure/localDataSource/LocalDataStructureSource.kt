package com.arakim.datastructurevisualization.data.repository.dataStructure.localDataSource

import android.util.Log
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import javax.inject.Inject

internal class LocalDataStructureSource @Inject constructor() {

    fun create(name: String, type: DataStructureType) {
        Log.d("Test","create: $name")
    }

}