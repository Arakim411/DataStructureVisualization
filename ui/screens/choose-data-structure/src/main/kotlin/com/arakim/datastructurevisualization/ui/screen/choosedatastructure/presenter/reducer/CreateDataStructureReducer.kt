package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer

import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.CreateDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.State
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel.BinarySearchTree
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel.HashMap
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel.LinkedList
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.util.getWithNewItem
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructureType
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.CreateDataStructureUseCase
import java.util.UUID
import javax.inject.Inject

class CreateDataStructureReducer @Inject constructor(
    private val createDataStructureUseCase: CreateDataStructureUseCase,
) : StateReducer<State, Action, CreateDataStructureAction>() {

    override fun State.reduce(action: CreateDataStructureAction): State = when (this) {
        is ReadyState -> reduce(action)
        else -> logInvalidState()
    }

    private fun ReadyState.reduce(action: CreateDataStructureAction): State {
        createDataStructureUseCase(action.name, action.type.toDomain())

        val newItem = DataStructureUiModel(
            id = UUID.randomUUID().toString(),
            customName = action.name,
            dataStructureType = action.type,
        )
        return copy(dataStructures = dataStructures.getWithNewItem(newItem))
    }

    private fun DataStructureTypeUiModel.toDomain(): DataStructureType = when(this){
        BinarySearchTree -> DataStructureType.BinarySearchTree
        HashMap -> DataStructureType.HashMap
        LinkedList -> DataStructureType.LinkedList
    }
}