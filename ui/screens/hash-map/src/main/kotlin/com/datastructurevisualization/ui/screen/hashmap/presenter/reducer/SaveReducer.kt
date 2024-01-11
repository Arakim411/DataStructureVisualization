package com.datastructurevisualization.ui.screen.hashmap.presenter.reducer

import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.ui.mvi.StateReducerWithSideEffect
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.SaveDataStructureUseCase
import com.datastructurevisualization.ui.screen.hashmap.presenter.Action
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.SaveAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapSideEffect
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapSideEffect.SavedSideEffect
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ReadyState
import com.datastructurevisualization.ui.screen.hashmap.presenter.State
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.HashMapVisualizationBuilder
import javax.inject.Inject

class SaveReducer @Inject constructor(
    private val hashMapVisualizationBuilder: HashMapVisualizationBuilder,
    private val saveDataStructureUseCase: SaveDataStructureUseCase,
) : StateReducerWithSideEffect<State, Action, SaveAction, HashMapSideEffect>() {

    override fun State.reduce(action: SaveAction): State = when (this) {
        is ReadyState -> {
            coroutineScope.yielded {
                saveDataStructure()
            }
            this
        }

        else -> logInvalidState()
    }

    private suspend fun ReadyState.saveDataStructure() {
        val json = hashMapVisualizationBuilder.serializeToJson()
        saveDataStructureUseCase(id, json)
        emitSideEffect(SavedSideEffect)
    }
}