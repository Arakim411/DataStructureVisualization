package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter

import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.mvi.ReducerPresenterWithSideEffect
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.InitializationAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.SaveAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.IdleState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers.InitializeReducer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers.SaveReducer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers.UpdateTreeReducer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.BinarySearchTreeVisualizationBuilder
import javax.inject.Inject

internal typealias State = BinarySearchTreeState
typealias Action = BinarySearchTreeAction
internal typealias SideEffect = BinarySearchTreeSideEffect


@Stable
class BinarySearchTreePresenter @Inject constructor(
    val treeVisualizationBuilder: BinarySearchTreeVisualizationBuilder,
    initializeReducer: InitializeReducer,
    updateTreeReducer: UpdateTreeReducer,
    saveReducer: SaveReducer,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    init {
        registerReducer<UpdateTreeAction>(updateTreeReducer)
        registerReducer<InitializationAction>(initializeReducer)
        registerReducer<SaveAction>(saveReducer)
    }

}