package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer

import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastructurevisualization.kotlinutil.onFailure
import com.arakim.datastructurevisualization.kotlinutil.onSuccess
import com.arakim.datastructurevisualization.ui.mvi.StateReducerWithSideEffect
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializedAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializedFailedAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureSideEffect
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureSideEffect.FailedToGetDataStructures
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ErrorState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.IdleState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.InitializingState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.State
import com.arakim.datastructurevisualization.ui.common.uimodel.toUiModel
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.ListenForDataStructuresUpdateUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import javax.inject.Inject

class InitializeReducer @Inject constructor(
    private val listenForUpdates: ListenForDataStructuresUpdateUseCase,
) : StateReducerWithSideEffect<State, Action, InitializationAction,ChooseDataStructureSideEffect>() {

    private var listenForUpdatesJob: Job? = null

    override fun State.reduce(action: InitializationAction): State = when (action) {
        InitializeAction -> reduceInitializeAction()
        is InitializedAction -> reduceInitializedAction(action)
        InitializedFailedAction -> reduceInitializedFailedAction()
    }

    private fun State.reduceInitializeAction(): State = when (this) {
        IdleState -> {
            initialize()
            InitializingState
        }

        else -> logInvalidState()
    }

    private fun State.reduceInitializedAction(
        action: InitializedAction
    ): State = when (this) {
        InitializingState -> ReadyState(action.dataStructures)
        is ReadyState -> copy(dataStructures = action.dataStructures)
        else -> logInvalidState()
    }

    private fun State.reduceInitializedFailedAction(): State = when (this) {
        InitializingState -> ErrorState
        else -> logInvalidState()
    }

    private fun initialize() {
        listenForUpdatesJob?.cancel()

        listenForUpdatesJob = coroutineScope.launch {
            yield()
            listenForUpdates().collectLatest {
                onDataStructuresUpdate(it)
            }
        }
    }

    private fun onDataStructuresUpdate(result: TypedResult<List<DataStructure>, CommonError>) {
        result
            .onSuccess {
                onAction(InitializedAction(it.toUiModel()))
            }
            .onFailure {
                emitSideEffect(FailedToGetDataStructures)
            }
    }
}