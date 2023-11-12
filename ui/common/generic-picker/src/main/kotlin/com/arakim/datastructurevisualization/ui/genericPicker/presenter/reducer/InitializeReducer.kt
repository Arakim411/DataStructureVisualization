package com.arakim.datastructurevisualization.ui.genericPicker.presenter.reducer

import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.Action
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.InitializedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerState.IdleState
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerState.ReadyState
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.State
import javax.inject.Inject

class InitializeReducer @Inject constructor() : StateReducer<State, Action, InitializedAction>() {

    override fun State.reduce(action: InitializedAction): State = when (this) {
        IdleState -> ReadyState(
            allItems = action.allItems,
            floatingModalItems = action.floatingModalItems,
        )
        else -> logInvalidState()
    }
}