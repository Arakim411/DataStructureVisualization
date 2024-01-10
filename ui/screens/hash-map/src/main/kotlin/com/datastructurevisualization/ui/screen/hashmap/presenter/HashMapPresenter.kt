package com.datastructurevisualization.ui.screen.hashmap.presenter

import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.mvi.ReducerPresenterWithSideEffect
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.UpdateAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.IdleState
import com.datastructurevisualization.ui.screen.hashmap.presenter.reducer.HashMapInitializeReducer
import com.datastructurevisualization.ui.screen.hashmap.presenter.reducer.HashMapUpdateReducer
import com.datastructurevisualization.ui.screen.hashmap.presenter.visualizationbuilder.HashMapVisualizationBuilder
import javax.inject.Inject

internal typealias State = HashMapState
internal typealias Action = HashMapAction
internal typealias SideEffect = HashMapSideEffect

@Stable
class HashMapPresenter @Inject constructor(
    val hashMapVisualizationBuilder: HashMapVisualizationBuilder,
    initializeReducer: HashMapInitializeReducer,
    updateReducer: HashMapUpdateReducer,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    init {
        registerReducer<InitializationAction>(initializeReducer)
        registerReducer<UpdateAction>(updateReducer)
    }
}