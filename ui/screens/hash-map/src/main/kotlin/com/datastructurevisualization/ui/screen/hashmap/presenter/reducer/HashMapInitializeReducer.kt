package com.datastructurevisualization.ui.screen.hashmap.presenter.reducer

import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.GetDataStructureByIdUseCase
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction.InitializeAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction.InitializedFailedAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction.InitializedSuccessAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ErrorState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.InitializingState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ReadyState
import com.datastructurevisualization.ui.screen.hashmap.presenter.State
import kotlinx.coroutines.delay
import javax.inject.Inject

class HashMapInitializeReducer @Inject constructor(
    private val getDataStructureByIdUseCase: GetDataStructureByIdUseCase,
) : StateReducer<HashMapState, HashMapAction, InitializationAction>() {

    override fun HashMapState.reduce(action: InitializationAction): HashMapState = when (action) {
        is InitializeAction -> reduceInitializeAction(action.id)
        InitializedFailedAction -> ErrorState
        is InitializedSuccessAction -> ReadyState(
            id = action.id,
            customName = action.customName,
        )
    }

    private fun State.reduceInitializeAction(id: Int): State {
        coroutineScope.yielded {
            delay(3000) //TODO implement
            onAction(InitializedSuccessAction(id, "Test"))
        }
        return InitializingState
    }
}