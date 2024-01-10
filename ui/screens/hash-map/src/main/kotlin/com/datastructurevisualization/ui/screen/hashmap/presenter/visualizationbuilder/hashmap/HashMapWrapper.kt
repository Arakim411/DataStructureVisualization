package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.hashmap

abstract class HashMapWrapper {

    private val listeners = mutableSetOf<HashMapWrapperListener>()
    private var bucketsCount: Int = -1

    fun initializeBuckets(bucketsCount: Int) {
        if (this.bucketsCount != -1) throw Exception("Buckets already initialized")
        this.bucketsCount = bucketsCount
        listeners.forEach { it.onBucketsInitialized(bucketsCount) }
    }

    fun addListener(listener: HashMapWrapperListener) {
        listeners.add(listener)
    }
}