package com.arakim.datastructurevisualization.navigation.uicontroller.controllers

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import com.arakim.datastructurevisualization.navigation.uicontroller.UiControllerType.ModalDrawer
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerGroup
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerItem
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Composable
internal fun ModalNavigationDrawer(
    uiControllerType: ModalDrawer,
    navigationGroups: ImmutableList<NavUiControllerGroup>,
    selectedRoute: String,
    onItemClick: (NavUiControllerItem) -> Unit,
    content: @Composable () -> Unit,
) {

    ModalNavigationDrawer(
        drawerState = uiControllerType.drawerState,
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
