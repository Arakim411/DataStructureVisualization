package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter

import androidx.compose.runtime.Stable
import com.arakim.datastructurevisualization.ui.mvi.ReducerPresenterWithSideEffect
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction.InitializationAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.IdleState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.reducer.DeletedDataStructureInitializeReducer
import javax.inject.Inject

typealias State = DeletedDataStructuresState
typealias Action = DeletedDataStructureAction
typealias SideEffect = DeletedDataStructureSideEffect

@Stable
class DeletedDataStructuresPresenter @Inject constructor(
    private val initializeReducer: DeletedDataStructureInitializeReducer,
): ReducerPresenterWithSideEffect<State, Action, SideEffect>(IdleState) {

    init {
        registerReducer<InitializationAction>(initializeReducer)
    }

}