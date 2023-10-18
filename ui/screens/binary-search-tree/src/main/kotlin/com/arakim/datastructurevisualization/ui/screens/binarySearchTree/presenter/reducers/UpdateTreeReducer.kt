package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers

import android.util.Log
import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.Action
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.DeleteAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.FindAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.InsertAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.ReadyState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.State
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.helpers.BinarySearchTreeVisualizationBuilder
import javax.inject.Inject

class UpdateTreeReducer @Inject constructor(
    private val visualizationBuilder: BinarySearchTreeVisualizationBuilder,
) : StateReducer<State, Action, UpdateTreeAction>() {

    override fun State.reduce(action: UpdateTreeAction): State = when (this) {
        is ReadyState -> reduceUpdateTreeAction(action)
    }

    private fun ReadyState.reduceUpdateTreeAction(action: UpdateTreeAction): State {
        when (action) {
            is DeleteAction -> Unit
            is FindAction -> Unit
            is InsertAction ->  visualizationBuilder.insert(action.value)
        }
        return this
    }
}