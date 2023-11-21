package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer

import com.arakim.datastructurevisualization.domain.util.yielded
import com.arakim.datastructurevisualization.ui.mvi.StateReducer
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.UpdateDataStructureIsFavoriteAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.State
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.UpdateDataStructureIsFavoriteUseCase
import javax.inject.Inject

class UpdateDataStructureIsFavoriteReducer @Inject constructor(
    private val update: UpdateDataStructureIsFavoriteUseCase,
) : StateReducer<State, Action, UpdateDataStructureIsFavoriteAction>() {

    override fun State.reduce(action: UpdateDataStructureIsFavoriteAction): State {
        coroutineScope.yielded {
            update(action.id, action.isFavorite)
        }
      return  this
    }
}