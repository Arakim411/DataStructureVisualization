package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer

import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.DeleteDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.State
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.DeleteDataStructureUseCase
import javax.inject.Inject

class DeleteDataStructureReducer @Inject constructor(
    private val deleteDataStructure: DeleteDataStructureUseCase,
) : StateReducer<State, Action, DeleteDataStructureAction>() {

    override fun State.reduce(action: DeleteDataStructureAction): State {
        coroutineScope.yielded {
            deleteDataStructure.invoke(action.id)
        }
        return this
    }


}