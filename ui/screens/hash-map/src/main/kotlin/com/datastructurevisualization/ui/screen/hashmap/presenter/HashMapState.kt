package com.datastructurevisualization.ui.screen.hashmap.presenter

import androidx.compose.runtime.Immutable

sealed interface HashMapState {

    @Immutable
    object IdleState : HashMapState

    @Immutable
    object InitializingState : HashMapState

    @Immutable
    object ErrorState : HashMapState

    @Immutable
    data class ReadyState(
        val id: Int,
        val customName: String,
    ) : HashMapState
}