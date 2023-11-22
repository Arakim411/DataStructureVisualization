package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.helpers

import com.arakim.datastructurevisualization.kotlinutil.DataStructureSerializer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.BinarySearchTreeVisualizationBuilder
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.tree.getAllValuesPreOrder
import com.google.gson.Gson
import javax.inject.Inject

class BinarySearchTreeSerializer @Inject constructor() : DataStructureSerializer {

    lateinit var visualizationBuilder: BinarySearchTreeVisualizationBuilder

    fun initialize(visualizationBuilder: BinarySearchTreeVisualizationBuilder) {
        this.visualizationBuilder = visualizationBuilder
    }

    override fun serializeToJson(): String {
        val values = visualizationBuilder.root?.getAllValuesPreOrder() ?: return ""

        return Gson().toJson(values)
    }

    override fun createFromJson(json: String) {
        val values = Gson().fromJson(json, Array<Number>::class.java)
        values.forEachIndexed { index, number ->
            visualizationBuilder.insert(
                number = number,
            )
        }
    }
}


