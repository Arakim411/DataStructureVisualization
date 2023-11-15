package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter

import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.mvi.ReducerPresenterWithSideEffect
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.CreateDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.IdleState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer.CreateDataStructureReducer
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer.InitializeReducer
import javax.inject.Inject

internal typealias State = ChooseDataStructureState
internal typealias Action = ChooseDataStructureAction
internal typealias SideEffect = ChooseDataStructureSideEffect

@Stable
class ChooseDataStructurePresenter @Inject constructor(
    initializeReducer: InitializeReducer,
    createDataStructureReducer: CreateDataStructureReducer,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    init {
        registerReducer<InitializationAction>(initializeReducer)
        registerReducer<CreateDataStructureAction>(createDataStructureReducer)
    }
}