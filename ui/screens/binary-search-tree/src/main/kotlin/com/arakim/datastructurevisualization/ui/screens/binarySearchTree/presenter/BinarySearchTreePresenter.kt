package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter

import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.mvi.ReducerPresenter
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.ReadyState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.BinarySearchTreeVisualizationBuilder
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers.UpdateTreeReducer
import com.arakim.datastructurevisualization.ui.visualizationbuilder.visualizationEngine.presenter.model.DefaultVisualizationSetUp
import javax.inject.Inject

internal typealias State = BinarySearchTreeState
typealias Action = BinarySearchTreeAction

@Stable
class BinarySearchTreePresenter @Inject constructor(
    val treeVisualizationBuilder: BinarySearchTreeVisualizationBuilder,
    updateTreeReducer: UpdateTreeReducer,
) : ReducerPresenter<State, Action>(ReadyState) {

    init {
        registerReducer<UpdateTreeAction>(updateTreeReducer)
    }

    override fun onInitialized() {
        super.onInitialized()
        //TODO dp values depends of screen
        treeVisualizationBuilder.initialize(DefaultVisualizationSetUp)
    }

}