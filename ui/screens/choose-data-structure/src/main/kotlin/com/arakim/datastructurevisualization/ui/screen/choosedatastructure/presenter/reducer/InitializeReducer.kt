package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer

import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializedAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializedFailedAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ErrorState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.IdleState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.InitializingState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.State
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import javax.inject.Inject

class InitializeReducer @Inject constructor() : StateReducer<State, Action, InitializationAction>() {

    override fun State.reduce(action: InitializationAction): State = when (action) {
        InitializeAction -> reduceInitializeAction()
        is InitializedAction -> reduceInitializedAction(action)
        InitializedFailedAction -> reduceInitializedFailedAction()
    }

    private fun State.reduceInitializeAction(): State = when (this) {
        IdleState -> {
            coroutineScope.launch {
                yield()
                initialize()
            }
            InitializingState
        }

        else -> logInvalidState()
    }

    private fun State.reduceInitializedAction(
        action: InitializedAction
    ): State = when (this) {
        InitializingState -> ReadyState(action.dataStructures)
        else -> logInvalidState()
    }

    private fun State.reduceInitializedFailedAction(): State = when (this) {
        InitializingState -> ErrorState
        else -> logInvalidState()
    }

    private suspend fun initialize() {
        //TODO load data structures from data base
        delay(100)
        onAction(InitializedAction(immutableListOf()))
    }
}