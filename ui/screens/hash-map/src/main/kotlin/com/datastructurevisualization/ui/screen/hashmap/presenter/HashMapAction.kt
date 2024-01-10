package com.datastructurevisualization.ui.screen.hashmap.presenter

sealed interface HashMapAction {

    sealed interface InitializationAction : HashMapAction {
        data class InitializeAction(val id: Int) : InitializationAction
        data class InitializedSuccessAction(
            val id: Int,
            val customName: String,
        ) : InitializationAction

        object InitializedFailedAction : InitializationAction
        object OnHashMapCreated: InitializationAction
    }

    object SaveAction : HashMapAction

    sealed interface UpdateAction: HashMapAction {
        data class InsertAction(val value: Number) : UpdateAction
        data class DeleteAction(val value: Number) : UpdateAction
        data class AddRandomValuesAction(val count: Int): UpdateAction
    }

}