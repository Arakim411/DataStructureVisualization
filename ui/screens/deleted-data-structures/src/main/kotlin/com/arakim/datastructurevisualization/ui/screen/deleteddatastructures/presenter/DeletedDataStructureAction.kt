package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter

import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureUiModel

sealed interface DeletedDataStructureAction {

    data class StopDeletionProcessAction(
        val dataStructureUiModel: DataStructureUiModel
    ) : DeletedDataStructureAction

    sealed interface InitializationAction : DeletedDataStructureAction {
        object InitializeAction : InitializationAction
        object InitializeErrorAction : InitializationAction
        data class InitializedAction(val dataStructures: List<DataStructureUiModel>) : InitializationAction
    }
}