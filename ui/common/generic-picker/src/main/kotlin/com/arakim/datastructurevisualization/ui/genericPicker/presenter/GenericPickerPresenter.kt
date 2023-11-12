package com.arakim.datastructurevisualization.ui.genericPicker.presenter

import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.InitializedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerState.IdleState
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.reducer.InitializeReducer
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.reducer.NewDataPickReducer
import com.arakim.datastructurevisualization.ui.mvi.ReducerPresenterWithSideEffect
import javax.inject.Inject

internal typealias State = GenericPickerState
internal typealias Action = GenericPickerAction
internal typealias SideEffect = GenericPickerSideEffect

@Stable
class GenericPickerPresenter @Inject constructor(
    initializeReducer: InitializeReducer,
    newDataPickedReducer: NewDataPickReducer,
) : ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    init {
        registerReducer<InitializedAction>(initializeReducer)
        registerReducer<NewDataPickedAction>(newDataPickedReducer)
    }
}