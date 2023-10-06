package com.arakim.datastructurevisualization.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiController
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerGroup
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerItem
import com.arakim.datastructurevisualization.navigation.uicontroller.rememberNavUiControllerState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.BinarySearchTreeScreen
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.FakeWindowSizeType

//TODO add intent filters
@Composable
fun MainNavigation() {

    val navController = rememberNavController()
    val navUiController = rememberNavUiControllerState()

    NavigationUiController(
        navigationUiControllerState = navUiController,
        navUiControllerGroups = getNavigationGroups(),
        onItemClick = {
            navController.navigate(it.route)
        },
        selectedRoute = navController.currentDestination?.route ?: "",
    ) {
        NavHost(
            navController = navController,
            startDestination = MainNavDestinations.BinarySearchTree.route
        ) {
            MainNavDestinations.values().forEach { destination ->
                composable(destination.route) {
                    Screen(
                        destination = destination,
                        navUiController = navUiController,
                    )
                }
            }
        }
    }
}

@Composable
fun Screen(
    destination: MainNavDestinations,
    navUiController: NavigationUiControllerState,
) {
    when (destination) {
        MainNavDestinations.BinarySearchTree -> BinarySearchTreeScreen(navUiController)
        MainNavDestinations.Stack -> Unit
        MainNavDestinations.Queue -> Unit
        MainNavDestinations.LinkedList -> Unit
        MainNavDestinations.HashTable -> Unit
        MainNavDestinations.Favorite -> Unit
        MainNavDestinations.Delete -> Unit
    }
}

@Composable
@Stable
private fun getNavigationGroups(): ImmutableList<NavUiControllerGroup> = immutableListOf(
    getDataStructuresGroup(),
    getOtherGroup()
)

@Composable
@Stable
private fun getDataStructuresGroup(): NavUiControllerGroup = NavUiControllerGroup(
    name = stringResource(id = R.string.group_name_data_structure),
    items = immutableListOf(
        MainNavDestinations.BinarySearchTree.toNavItem(),
        MainNavDestinations.Stack.toNavItem(),
        MainNavDestinations.Queue.toNavItem(),
        MainNavDestinations.LinkedList.toNavItem(),
        MainNavDestinations.HashTable.toNavItem(),
    ),
)

@Composable
@Stable
private fun getOtherGroup(): NavUiControllerGroup = NavUiControllerGroup(
    name = stringResource(id = R.string.group_name_other),
    items = immutableListOf(
        MainNavDestinations.Favorite.toNavItem(),
        MainNavDestinations.Delete.toNavItem(),
    ),
)

//TODO correct icons
@Composable
@Stable
private fun MainNavDestinations.toNavItem(): NavUiControllerItem = when (this) {
    MainNavDestinations.BinarySearchTree -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = R.drawable.ic_tree,
    )

    MainNavDestinations.Stack -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = R.drawable.ic_stack,
    )

    MainNavDestinations.Queue -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = R.drawable.ic_queue,
    )

    MainNavDestinations.LinkedList -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = R.drawable.ic_linked_list,
    )

    MainNavDestinations.HashTable -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = R.drawable.ic_hash_table,
    )

    MainNavDestinations.Favorite -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = R.drawable.ic_favorite,
    )

    MainNavDestinations.Delete -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = R.drawable.ic_delete,
    )
}


@Preview
@Composable
fun MainNavigationPreview() {
    FakeWindowSizeType {
        MainNavigation()
    }
}