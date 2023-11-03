package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter

sealed interface BinarySearchTreeAction {

    sealed interface UpdateTreeAction : BinarySearchTreeAction {
        data class InsertAction(val value: Number) : UpdateTreeAction
        data class DeleteAction(val value: Number) : UpdateTreeAction
        data class FindAction(val value: Number) : UpdateTreeAction
    }
}