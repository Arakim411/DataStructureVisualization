package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter

sealed interface BinarySearchTreeAction {

    sealed interface InitializationAction: BinarySearchTreeAction{
        data class InitializeAction(val id: Int) : InitializationAction
        data class InitializedSuccessAction(val customName: String): InitializationAction
        object InitializedFailedAction: InitializationAction
    }



    sealed interface UpdateTreeAction : BinarySearchTreeAction {
        data class InsertAction(val value: Number) : UpdateTreeAction
        data class DeleteAction(val value: Number) : UpdateTreeAction
        data class FindAction(val value: Number) : UpdateTreeAction
    }
}