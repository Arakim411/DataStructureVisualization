package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.hashmap

import java.util.UUID
import kotlin.math.absoluteValue

abstract class HashMapWrapper {

    private val listeners = mutableSetOf<HashMapWrapperListener>()
    var bucketsCount: Int = -1
        private set

    private val _hashMap = hashMapOf<Int, MutableList<HashMapValue>>()
    val hashMap get() = _hashMap as Map<Int, List<HashMapValue>>

    fun initializeBuckets(bucketsCount: Int) {
        if (this.bucketsCount != -1) throw Exception("Buckets already initialized")
        this.bucketsCount = bucketsCount
        listeners.forEach { it.onBucketsInitialized(bucketsCount) }
    }

    fun addListener(listener: HashMapWrapperListener) {
        listeners.add(listener)
    }

    open fun addValue(value: Int) {
        val hash = getHash(value)
        addToBucket(bucket = hash, value = value)
    }

    open fun deleteValue(value: Int) {
        val hash = getHash(value)
        val indexToDelete = hashMap[hash]?.indexOfFirst { it.value == value }

        if (indexToDelete == -1 || indexToDelete == null) return

        val valueToDelete = hashMap[hash]!![indexToDelete]
        val valuesBeforeDeleted = hashMap[hash]?.toList() ?: emptyList()

        _hashMap[hash]?.removeAt(indexToDelete)

        listeners.forEach {
            it.onValueDeleted(
                valueToDelete,
                valuesBeforeDeleted,
                indexToDelete
            )
        }

    }

    private fun addToBucket(bucket: Int, value: Int) {
        val hashMapValue = HashMapValue(UUID.randomUUID().toString(), bucket, value)

        if (_hashMap[bucket] == null) {
            _hashMap[bucket] = mutableListOf(hashMapValue)
        } else {
            _hashMap[bucket]!!.add(hashMapValue)
        }
        val valuesInBucket = hashMap[bucket]?.dropLast(1) ?: emptyList()

        listeners.forEach { it.onValueAdded(hashMapValue, valuesInBucket) }
    }

    private fun getHash(value: Int): Int {
        val result = value % (bucketsCount + 1)
        return result.absoluteValue
    }
}