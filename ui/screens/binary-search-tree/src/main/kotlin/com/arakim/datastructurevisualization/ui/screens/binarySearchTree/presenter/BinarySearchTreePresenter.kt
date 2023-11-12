package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter

import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.mvi.ReducerPresenter
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.IdleState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers.InitializeReducer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers.UpdateTreeReducer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.BinarySearchTreeVisualizationBuilder
import javax.inject.Inject

internal typealias State = BinarySearchTreeState
typealias Action = BinarySearchTreeAction

@Stable
class BinarySearchTreePresenter @Inject constructor(
    val treeVisualizationBuilder: BinarySearchTreeVisualizationBuilder,
    initializeReducer: InitializeReducer,
    updateTreeReducer: UpdateTreeReducer,
) : ReducerPresenter<State, Action>(IdleState) {

    init {
        registerReducer<UpdateTreeAction>(updateTreeReducer)
        registerReducer<InitializeAction>(initializeReducer)
    }

    override fun onInitialized() {
        super.onInitialized()
        treeVisualizationBuilder.initialize(
            coroutineScope = coroutineScope,
            onInitialized = {
                onAction(InitializeAction)
            },
        )
    }

}