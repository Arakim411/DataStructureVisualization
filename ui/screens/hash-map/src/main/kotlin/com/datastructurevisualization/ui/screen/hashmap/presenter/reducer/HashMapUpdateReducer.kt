package com.datastructurevisualization.ui.screen.hashmap.presenter.reducer

import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.datastructurevisualization.ui.screen.hashmap.presenter.Action
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.UpdateAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.State
import javax.inject.Inject

class HashMapUpdateReducer @Inject constructor() : StateReducer<State, Action, UpdateAction>() {

    override fun State.reduce(action: UpdateAction): State {
        TODO("Not yet implemented")
    }
}