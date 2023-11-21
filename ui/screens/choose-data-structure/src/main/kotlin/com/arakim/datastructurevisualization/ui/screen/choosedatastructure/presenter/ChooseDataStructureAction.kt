package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter

import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.util.ImmutableList

sealed interface ChooseDataStructureAction {

    sealed interface InitializationAction : ChooseDataStructureAction {
        object InitializeAction : InitializationAction
        object InitializedFailedAction : InitializationAction

        data class InitializedAction(
            val dataStructures: ImmutableList<DataStructureUiModel>,
        ) : InitializationAction
    }

    data class CreateDataStructureAction(
        val name: String,
        val type: DataStructureTypeUiModel,
    ) : ChooseDataStructureAction

    data class DeleteDataStructureAction(
        val id: Int,
    ) : ChooseDataStructureAction
}