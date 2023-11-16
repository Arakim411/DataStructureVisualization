package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter

sealed interface ChooseDataStructureSideEffect {

    object FailedToGetDataStructures : ChooseDataStructureSideEffect
    object FailedToCreateDataStructures: ChooseDataStructureSideEffect
}