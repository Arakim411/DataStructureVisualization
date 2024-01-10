package com.datastructurevisualization.ui.screen.hashmap.presenter.reducer

import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.datastructurevisualization.ui.screen.hashmap.presenter.Action
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.UpdateAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.UpdateAction.AddRandomValuesAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.UpdateAction.DeleteAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.UpdateAction.InsertAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ReadyState
import com.datastructurevisualization.ui.screen.hashmap.presenter.State
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.HashMapVisualizationBuilder
import javax.inject.Inject

class HashMapUpdateReducer @Inject constructor(
    private val hashMapVisualizationBuilder: HashMapVisualizationBuilder,
) : StateReducer<State, Action, UpdateAction>() {

    override fun State.reduce(action: UpdateAction): State = when (action) {
        is AddRandomValuesAction -> reduceAddRandomValuesAction(action)
        is DeleteAction -> reduceDeleteAction(action)
        is InsertAction -> reduceInsertAction(action)
    }

    private fun State.reduceAddRandomValuesAction(action: AddRandomValuesAction): State = when (this) {
        is ReadyState -> {
            addRandomValues(action.count)
            this
        }

        else -> logInvalidState()
    }

    private fun State.reduceDeleteAction(action: DeleteAction): State = when (this) {
        is ReadyState -> {
            hashMapVisualizationBuilder.deleteValue(action.value.toInt())
            this
        }

        else -> logInvalidState()
    }

    private fun State.reduceInsertAction(action: InsertAction): State = when (this) {
        is ReadyState -> {
            hashMapVisualizationBuilder.addValue(action.value.toInt())
            this
        }

        else -> logInvalidState()
    }

    private fun addRandomValues(count: Int) {
        repeat(count) {
            hashMapVisualizationBuilder.addValue((-100..100).random())
        }
    }

}