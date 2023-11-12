package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter

import androidx.compose.runtime.Immutable

@Immutable
sealed interface BinarySearchTreeState {

    object IdleState: BinarySearchTreeState

    @Immutable
    object ReadyState: BinarySearchTreeState
}