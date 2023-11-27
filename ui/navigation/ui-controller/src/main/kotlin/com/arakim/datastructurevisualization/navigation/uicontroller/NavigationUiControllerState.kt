package com.arakim.datastructurevisualization.navigation.uicontroller

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.arakim.datastructurevisualization.navigation.uicontroller.UiControllerType.ModalDrawer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Stable
class NavigationUiControllerState(
    private val uiControllerType: UiControllerType,
) {

    private val _navigationType = MutableStateFlow(uiControllerType)

    val navigationType: StateFlow<UiControllerType> = _navigationType

    fun forceUiControllerType(uiControllerType: UiControllerType) {
        _navigationType.value = uiControllerType
    }

}

@Composable
fun rememberModalDrawerState(): ModalDrawer {
    val drawerState = rememberDrawerState(Closed)
    return remember {
        ModalDrawer(drawerState)
    }
}

@Stable
sealed interface UiControllerType {
    @Stable
    data class ModalDrawer(val drawerState: DrawerState) : UiControllerType

    @Stable
    object Drawer : UiControllerType
}

