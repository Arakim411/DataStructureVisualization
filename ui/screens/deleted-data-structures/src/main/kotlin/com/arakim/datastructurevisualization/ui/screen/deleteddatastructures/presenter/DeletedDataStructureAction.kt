package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter

import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureUiModel
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure

sealed interface DeletedDataStructureAction {

    sealed interface InitializationAction : DeletedDataStructureAction {
        object InitializeAction : InitializationAction
        object InitializeErrorAction : InitializationAction
        data class InitializedAction(val dataStructures: List<DataStructureUiModel>) : InitializationAction
    }
}