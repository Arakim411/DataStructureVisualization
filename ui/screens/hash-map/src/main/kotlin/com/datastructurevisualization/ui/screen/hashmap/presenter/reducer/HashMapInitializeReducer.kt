package com.datastructurevisualization.ui.screen.hashmap.presenter.reducer

import androidx.compose.runtime.mutableStateOf
import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.kotlinutil.getOrNull
import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.GetDataStructureByIdUseCase
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction.InitializeAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction.InitializedFailedAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction.InitializedSuccessAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction.OnHashMapCreated
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ErrorState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.IdleState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.InitializingState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ReadyState
import com.datastructurevisualization.ui.screen.hashmap.presenter.State
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.HashMapVisualizationBuilder
import javax.inject.Inject

class HashMapInitializeReducer @Inject constructor(
    private val hashMapVisualizationBuilder: HashMapVisualizationBuilder,
    private val getDataStructureById: GetDataStructureByIdUseCase,
) : StateReducer<HashMapState, HashMapAction, InitializationAction>() {

    override fun HashMapState.reduce(action: InitializationAction): HashMapState = when (action) {
        is InitializeAction -> reduceInitializeAction(action.id)
        InitializedFailedAction -> ErrorState
        is InitializedSuccessAction -> ReadyState(
            id = action.id,
            customName = action.customName,
            actionsInQueue = hashMapVisualizationBuilder.visualizationBuilder.visualizationCore.actionsInQueue,
            isHashMapCreated = mutableStateOf(false),
        )

        OnHashMapCreated -> reduceHashMapCreatedAction()
    }

    private fun State.reduceInitializeAction(id: Int): State = when (this) {
        ErrorState, IdleState -> {
            coroutineScope.yielded {
                initialize(id)
            }
            InitializingState
        }

        else -> logInvalidState()
    }


    private fun State.reduceHashMapCreatedAction(): State {
        (this as? ReadyState)?.isHashMapCreated?.value = true
        return this
    }

    private suspend fun initialize(id: Int) {
        val dataStructure = getDataStructureById(id).getOrNull() ?: kotlin.run {
            onAction(InitializedFailedAction)
            return
        }

        hashMapVisualizationBuilder.initialize(
            dataStructureId = id,
            coroutineScope = coroutineScope,
            binaryHashMapJson = dataStructure.dataStructureJson,
            onInitialized = {
                onAction(InitializedSuccessAction(id, dataStructure.name))
            },
            onHashMapCreated = {
                onAction(OnHashMapCreated)
            }
        )
    }
}