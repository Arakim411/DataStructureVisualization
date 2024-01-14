package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.reducer

import com.arakim.datastructurevisualization.kotlinutil.onSuccess
import com.arakim.datastructurevisualization.ui.mvi.StateReducerWithSideEffect
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction.StopDeletionProcessAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureSideEffect.StoppedDeletionProcess
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.SideEffect
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.State
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.StopDeletionProcessUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class StopDeletionProcessReducer @Inject constructor(
    private val stopDeletionProcess: StopDeletionProcessUseCase
) : StateReducerWithSideEffect<State, Action, StopDeletionProcessAction, SideEffect>() {

    override fun State.reduce(action: StopDeletionProcessAction): State {
        coroutineScope.launch {
            stopDeletionProcess(action.dataStructureUiModel.id).onSuccess {
                emitSideEffect(StoppedDeletionProcess(action.dataStructureUiModel.customName))
            }
        }
        return this
    }
}