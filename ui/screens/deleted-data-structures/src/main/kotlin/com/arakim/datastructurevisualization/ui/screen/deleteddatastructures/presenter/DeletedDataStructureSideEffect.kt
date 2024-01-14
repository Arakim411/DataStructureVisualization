package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter

sealed interface DeletedDataStructureSideEffect {

    data class StoppedDeletionProcess(val dataStructureName: String) : DeletedDataStructureSideEffect
}