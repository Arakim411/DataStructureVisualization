package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.reducer

import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.kotlinutil.onFailure
import com.arakim.datastructurevisualization.kotlinutil.onSuccess
import com.arakim.datastructurevisualization.ui.common.uimodel.toUiModel
import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction.InitializationAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction.InitializationAction.InitializeErrorAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction.InitializationAction.InitializedAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.ErrorState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.IdleState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.InitializingState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.State
import com.arakim.datastructurevisualization.ui.util.toImmutableList
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.DsResult
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.ListenForDeletedDataStructureUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DeletedDataStructureInitializeReducer @Inject constructor(
    private val listenForDeletedDataStructureUseCase: ListenForDeletedDataStructureUseCase,
) : StateReducer<State, Action, InitializationAction>() {

    private var listenForUpdatesJob: Job? = null

    override fun State.reduce(action: InitializationAction): State = when (action) {
        InitializeAction -> reduceInitializeAction()
        InitializeErrorAction -> reduceInitializeErrorAction()
        is InitializedAction -> reduceInitializedAction(action)
    }

    private fun State.reduceInitializeAction(): State = when (this) {
        ErrorState, IdleState -> {
            initialize()
            InitializingState
        }

        else -> logInvalidState()
    }

    private fun State.reduceInitializeErrorAction(): State = when(this){
        InitializingState -> ErrorState
        else -> logInvalidState()
    }

    private fun State.reduceInitializedAction(action: InitializedAction): State{
        return ReadyState(action.dataStructures.toImmutableList())
    }

    private fun initialize() {
        listenForUpdatesJob?.cancel()
        listenForUpdatesJob = coroutineScope.yielded {
            listenForDeletedDataStructureUseCase().onEach { result ->
                onDeletedDataStructureUpdates(result)
            }.launchIn(coroutineScope)
        }
    }

    private fun onDeletedDataStructureUpdates(result: DsResult) {
        result.onSuccess {
            onAction(InitializedAction(it.toUiModel()))
        }.onFailure {
            onAction(InitializeErrorAction)
        }
    }

}