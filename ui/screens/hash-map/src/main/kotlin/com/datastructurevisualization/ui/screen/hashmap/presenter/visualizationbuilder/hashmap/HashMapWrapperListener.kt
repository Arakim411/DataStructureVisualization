package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.hashmap

interface HashMapWrapperListener {

    fun onValueInserted(value: HashMapValue)
    fun onValueDeleted(value: HashMapValue)

    fun onBucketsInitialized(bucketsCount: Int)
}