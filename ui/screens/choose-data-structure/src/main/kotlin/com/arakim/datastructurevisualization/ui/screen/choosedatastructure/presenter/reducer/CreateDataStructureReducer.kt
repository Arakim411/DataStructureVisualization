package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer

import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.CreateDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.State
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.util.getWithNewItem
import java.util.UUID
import javax.inject.Inject

class CreateDataStructureReducer @Inject() constructor(

) : StateReducer<State, Action, CreateDataStructureAction>() {

    override fun State.reduce(action: CreateDataStructureAction): State = when(this){
        is ReadyState -> reduce(action)
        else -> logInvalidState()
    }

    private fun ReadyState.reduce(action: CreateDataStructureAction): State {
            //TODO save in database

        val newItem = DataStructureUiModel(
            id = UUID.randomUUID().toString(),
            customName = action.name,
            dataStructureType = action.type,
        )
        return copy(dataStructures = dataStructures.getWithNewItem(newItem))
    }

}