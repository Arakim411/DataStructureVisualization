package com.datastructurevisualization.ui.screen.hashmap.presenter

import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.mvi.ReducerPresenterWithSideEffect
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.IdleState
import com.datastructurevisualization.ui.screen.hashmap.presenter.reducer.HashMapInitializeReducer
import javax.inject.Inject

internal typealias State = HashMapState
internal typealias Action = HashMapAction
internal typealias SideEffect = HashMapSideEffect

@Stable
class HashMapPresenter @Inject constructor(
    initializeReducer: HashMapInitializeReducer,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    init {
        registerReducer<InitializationAction>(initializeReducer)
    }
}