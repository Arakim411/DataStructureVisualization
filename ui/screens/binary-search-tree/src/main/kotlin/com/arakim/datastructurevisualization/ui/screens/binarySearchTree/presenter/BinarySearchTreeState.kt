package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter

import androidx.compose.runtime.Immutable

@Immutable
sealed interface BinarySearchTreeState {

    object IdleState: BinarySearchTreeState

    object InitializingState: BinarySearchTreeState

    object ErrorState: BinarySearchTreeState

    @Immutable
    data class ReadyState(
        val id: Int,
        val customName: String
    ): BinarySearchTreeState
}