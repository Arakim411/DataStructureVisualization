package com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.helpers

import androidx.annotation.Keep
import com.arakim.datastructurevisualization.kotlinutil.DataStructureSerializer
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.HashMapVisualizationBuilder
import com.google.gson.Gson
import javax.inject.Inject

class HashMapSerializer @Inject constructor() : DataStructureSerializer {

    lateinit var visualizationBuilder: HashMapVisualizationBuilder

    fun initialize(visualizationBuilder: HashMapVisualizationBuilder) {
        this.visualizationBuilder = visualizationBuilder
    }

    override fun serializeToJson(): String {
        val values = visualizationBuilder.hashMap.values.toList().flatten().map { it.value }
        val dto = HashMapDto(
            bucketsCount = visualizationBuilder.bucketsCount,
            values = values
        )

        return Gson().toJson(dto)
    }

    override fun createFromJson(json: String) {
        val dto = Gson().fromJson(json, HashMapDto::class.java)
        visualizationBuilder.initializeBuckets(dto.bucketsCount)
        dto.values.forEach {
            visualizationBuilder.addValue(it)
        }

    }
}

@Keep
private data class HashMapDto(val bucketsCount: Int, val values: List<Int>)