package com.arakim.datastructurevisualization.navigation.uicontroller.controllers

import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.arakim.datastructurevisualization.navigation.uicontroller.UiControllerType
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerGroup
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerItem
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Composable
internal fun NavigationDrawer(
    navigationUiControllerState: NavigationUiControllerState,
    navigationGroups: ImmutableList<NavUiControllerGroup>,
    selectedRoute: String,
    onItemClick: (NavUiControllerItem) -> Unit,
    content: @Composable () -> Unit,
) {

    PermanentNavigationDrawer(
        drawerContent = {
            DrawerContent(
                navigationGroups = navigationGroups,
                onItemClick = onItemClick,
                selectedRoute = selectedRoute,
            )
        },
    ) {
        content()
    }
}