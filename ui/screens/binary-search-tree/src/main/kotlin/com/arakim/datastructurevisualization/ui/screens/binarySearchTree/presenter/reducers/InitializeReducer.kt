package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers

import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.Action
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.IdleState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.ReadyState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.State
import javax.inject.Inject

class InitializeReducer @Inject constructor() : StateReducer<State, Action, InitializeAction>() {

    override fun State.reduce(action: InitializeAction): State = when (this) {
        IdleState -> ReadyState
        else -> logInvalidState()
    }
}