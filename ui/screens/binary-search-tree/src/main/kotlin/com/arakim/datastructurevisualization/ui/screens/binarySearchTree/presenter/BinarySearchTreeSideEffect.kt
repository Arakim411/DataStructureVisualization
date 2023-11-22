package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter

sealed interface BinarySearchTreeSideEffect {
    object SavedSideEffect: BinarySearchTreeSideEffect
}