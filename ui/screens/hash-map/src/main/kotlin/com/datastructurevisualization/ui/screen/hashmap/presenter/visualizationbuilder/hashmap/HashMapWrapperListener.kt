package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.hashmap

interface HashMapWrapperListener {

    fun onValueAdded(value: HashMapValue, valuesInBucket: List<HashMapValue>) = Unit
    fun onValueDeleted(value: HashMapValue) = Unit

    fun onBucketsInitialized(bucketsCount: Int) = Unit
}