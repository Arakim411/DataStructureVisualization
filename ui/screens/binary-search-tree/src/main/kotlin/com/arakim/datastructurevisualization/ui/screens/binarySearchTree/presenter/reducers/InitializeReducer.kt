package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.reducers

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.kotlinutil.getOrNull
import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.Action
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.InitializationAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.InitializationAction.InitializedFailedAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.InitializationAction.InitializedSuccessAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.InitializationAction.TreeCreatedAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.ErrorState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.IdleState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.InitializingState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.ReadyState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.State
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.treeVisualizationBuilder.BinarySearchTreeVisualizationBuilder
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.GetDataStructureByIdUseCase
import javax.inject.Inject

class InitializeReducer @Inject constructor(
    private val treeVisualizationBuilder: BinarySearchTreeVisualizationBuilder,
    private val getDataStructure: GetDataStructureByIdUseCase,
) : StateReducer<State, Action, InitializationAction>() {


    override fun State.reduce(action: InitializationAction): State = when (action) {
        is InitializeAction -> reduceInitializeAction(action)
        InitializedFailedAction -> reduceInitializedFailedAction()
        is InitializedSuccessAction -> reduceInitializedSuccessAction(action)
        TreeCreatedAction -> reduceTreeCreatedAction()
    }

    private fun State.reduceInitializeAction(action: InitializeAction): State = when (this) {
        ErrorState, IdleState -> {
            coroutineScope.yielded { initialize(action.id) }
            InitializingState
        }

        else -> logInvalidState()
    }

    private fun State.reduceInitializedFailedAction(): State = when (this) {
        InitializingState -> ErrorState
        else -> logInvalidState()
    }

    private fun State.reduceInitializedSuccessAction(action: InitializedSuccessAction): State = when (this) {
        InitializingState -> ReadyState(
            id = action.id,
            customName = action.customName,
            isTreeCreated = mutableStateOf(false),
        )

        else -> logInvalidState()
    }

    private fun State.reduceTreeCreatedAction(): State = when(this){
        is ReadyState -> {
            isTreeCreated.value = true
            this
        }
        else -> logInvalidState()
    }

    private suspend fun initialize(id: Int) {
        val dataStructure = getDataStructure(id).getOrNull() ?: kotlin.run {
            onAction(InitializedFailedAction)
            return
        }

        treeVisualizationBuilder.initialize(
            dataStructureId = id,
            binarySearchTreeJson = dataStructure.dataStructureJson,
            coroutineScope = coroutineScope,
            onInitialized = {
                onAction(
                    InitializedSuccessAction(
                        customName = dataStructure.name,
                        id = id,
                    )
                )
            },
            onTreeCreated = {
                onAction(TreeCreatedAction)
            }
        )
    }
}