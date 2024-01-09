package com.datastructurevisualization.ui.screen.hashmap.presenter

sealed interface HashMapAction {

    sealed interface InitializationAction : HashMapAction {
        data class InitializeAction(val id: Int) : InitializationAction
        data class InitializedSuccessAction(
            val id: Int,
            val customName: String,
        ) : InitializationAction

        object InitializedFailedAction : InitializationAction
    }

}