package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.StateFlow

@Immutable
sealed interface BinarySearchTreeState {

    object IdleState: BinarySearchTreeState

    object InitializingState: BinarySearchTreeState

    object ErrorState: BinarySearchTreeState

    @Immutable
    data class ReadyState(
        val id: Int,
        val customName: String,
        val isTreeCreated: MutableState<Boolean>,
        val actionsInQueue: StateFlow<Int>,
    ): BinarySearchTreeState
}