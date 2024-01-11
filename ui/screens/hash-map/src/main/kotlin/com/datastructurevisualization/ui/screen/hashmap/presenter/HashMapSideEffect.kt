package com.datastructurevisualization.ui.screen.hashmap.presenter

sealed interface HashMapSideEffect {

    object SavedSideEffect : HashMapSideEffect
}