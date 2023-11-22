package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.helpers

import com.arakim.datastructurevisualization.kotlinutil.DataStructureSerializer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.BinarySearchTreeVisualizationBuilder
import org.json.JSONObject
import javax.inject.Inject

class BinarySearchTreeSerializer @Inject constructor() : DataStructureSerializer {

    lateinit var visualizationBuilder: BinarySearchTreeVisualizationBuilder

    fun initialize(visualizationBuilder: BinarySearchTreeVisualizationBuilder) {
        this.visualizationBuilder = visualizationBuilder
    }

    override fun serializeToJson(): String {
        val map = mapOf<String,String>()
        JSONObject(map)

        return ""
    }

    override fun createFromJson(json: String) {
      //  TODO("Not yet implemented")
    }
}