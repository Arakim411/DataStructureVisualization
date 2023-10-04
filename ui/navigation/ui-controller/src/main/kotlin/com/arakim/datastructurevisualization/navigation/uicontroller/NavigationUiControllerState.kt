package com.arakim.datastructurevisualization.navigation.uicontroller

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class NavigationUiControllerState {

    private val _navigationType: MutableStateFlow<NavigationOverlayType> =
        MutableStateFlow(NavigationOverlayType.None)

    val navigationType: StateFlow<NavigationOverlayType> = _navigationType

    fun setNavigationOverlayType(navigationOverlayType: NavigationOverlayType) {
        _navigationType.value = navigationOverlayType
    }
}

@Stable
sealed interface NavigationOverlayType {
    @Stable
    data class Modal(val drawerState: DrawerState) : NavigationOverlayType

    @Stable
    object None : NavigationOverlayType
}

@Composable
fun rememberNavUiControllerState(): NavigationUiControllerState = remember {
    NavigationUiControllerState()
}


