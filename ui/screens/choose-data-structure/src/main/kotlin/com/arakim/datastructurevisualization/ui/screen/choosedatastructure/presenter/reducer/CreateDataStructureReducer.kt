package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer

import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.kotlinutil.onFailure
import com.arakim.datastructurevisualization.ui.mvi.StateReducerWithSideEffect
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.CreateDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureSideEffect
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureSideEffect.FailedToGetDataStructures
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.State
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.BinarySearchTree
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.HashMap
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.LinkedList
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.CreateDataStructureUseCase
import javax.inject.Inject

class CreateDataStructureReducer @Inject constructor(
    private val createDataStructureUseCase: CreateDataStructureUseCase,
) : StateReducerWithSideEffect<State, Action, CreateDataStructureAction,ChooseDataStructureSideEffect>() {

    override fun State.reduce(action: CreateDataStructureAction): State = when (this) {
        is ReadyState -> reduce(action)
        else -> logInvalidState()
    }

    private fun ReadyState.reduce(action: CreateDataStructureAction): State {
        coroutineScope.yielded {
            createDataStructureUseCase(action.name, action.type.toDomain())
                .onFailure {
                    emitSideEffect(FailedToGetDataStructures)
                }
        }

        return this
    }

    private fun DataStructureTypeUiModel.toDomain(): DataStructureType = when (this) {
        BinarySearchTree -> DataStructureType.BinarySearchTree
        HashMap -> DataStructureType.HashMap
        LinkedList -> DataStructureType.LinkedList
    }
}