package com.arakim.datastructurevisualization.ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.navigation.uicontroller.UiControllerType
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import kotlinx.coroutines.launch

//TODO name
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String,
    onNavigationAction: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (onNavigationAction != null) {
                IconButton(onClick = onNavigationAction) {
                    Icon(imageVector = Icons.Filled.Menu, contentDescription = null)
                }
            }
        },
        actions = actions,
    )
}

@Composable
fun CommonTopAppBar(
    title: String,
    navigationUiControllerState: NavigationUiControllerState,
    actions: @Composable RowScope.() -> Unit = {},
) {

    val coroutineScope = rememberCoroutineScope()
    val navigationType = navigationUiControllerState.navigationType.collectAsStateWithLifecycle().value

    val navAction = remember(navigationType) {
        (navigationType as? UiControllerType.ModalDrawer)?.let {
            {
                coroutineScope.launch {
                    it.drawerState.open()
                }
                Unit
            }
        }
    }

    CommonTopAppBar(
        title = title,
        actions = actions,
        onNavigationAction = navAction,
    )
}