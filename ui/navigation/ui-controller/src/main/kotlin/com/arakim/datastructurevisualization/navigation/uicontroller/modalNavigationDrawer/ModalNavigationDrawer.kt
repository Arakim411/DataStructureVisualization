package com.arakim.datastructurevisualization.navigation.uicontroller.modalNavigationDrawer

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationOverlayType
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerGroup
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerItem
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Composable
internal fun ModalNavigationDrawer(
    navigationUiControllerState: NavigationUiControllerState,
    navigationGroups: ImmutableList<NavUiControllerGroup>,
    selectedRoute: String,
    onItemClick: (NavUiControllerItem) -> Unit,
    content: @Composable () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    LaunchedEffect(Unit) {
        navigationUiControllerState.setNavigationOverlayType(
            NavigationOverlayType.Modal(drawerState),
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
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

@Composable
private fun DrawerContent(
    navigationGroups: ImmutableList<NavUiControllerGroup>,
    onItemClick: (NavUiControllerItem) -> Unit,
    selectedRoute: String,
) {
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(12.dp))

        navigationGroups.forEachIndexed { index, group ->
            NavigationDrawerGroup(
                group = group,
                onItemClick = onItemClick,
                selectedRoute = selectedRoute,
                userDivider = index != 0,
            )
        }
    }
}

@Composable
private fun NavigationDrawerGroup(
    group: NavUiControllerGroup,
    onItemClick: (NavUiControllerItem) -> Unit,
    selectedRoute: String,
    userDivider: Boolean,
) {
    if (userDivider) {
        Spacer(modifier = Modifier.height(drawerMargin))
        Divider()
    }
    Spacer(modifier = Modifier.height(drawerMargin))
    NavigationDrawerHeadLine(text = group.name)
    Spacer(modifier = Modifier.height(drawerMargin))

    group.items.forEach { item ->
        NavigationDrawerItem(
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
            label = { Text(text = item.title) },
            selected = item.route == selectedRoute,
            onClick = { onItemClick(item) },
            icon = { Icon(painter = painterResource(id = item.iconId), contentDescription = null) }
        )
    }
}

@Composable
private fun NavigationDrawerHeadLine(text: String) {
    Text(
        modifier = Modifier.padding(start = drawerMargin * 2),
        text = text,
        style = MaterialTheme.typography.headlineSmall,
        fontSize = 14.sp
    )
}

private val drawerMargin = 12.dp
