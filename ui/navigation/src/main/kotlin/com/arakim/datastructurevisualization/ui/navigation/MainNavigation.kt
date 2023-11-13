package com.arakim.datastructurevisualization.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
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
import com.arakim.datastructurevisualization.ui.navigation.MainNavDestinations.ChooseDataStructureDestination
import com.arakim.datastructurevisualization.ui.navigation.MainNavDestinations.DeletedDestination
import com.arakim.datastructurevisualization.ui.navigation.MainNavDestinations.FavoritesDestination
import com.arakim.datastructurevisualization.ui.navigation.R.drawable
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
            startDestination = MainNavDestinations.ChooseDataStructureDestination.route
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
        ChooseDataStructureDestination -> TODO()
        FavoritesDestination -> TODO()
        DeletedDestination -> TODO()
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
        ChooseDataStructureDestination.toNavItem(),
    ),
)

@Composable
@Stable
private fun getOtherGroup(): NavUiControllerGroup = NavUiControllerGroup(
    name = stringResource(id = R.string.group_name_other),
    items = immutableListOf(
        FavoritesDestination.toNavItem(),
        DeletedDestination.toNavItem(),
    ),
)

//TODO correct icons
@Composable
@Stable
private fun MainNavDestinations.toNavItem(): NavUiControllerItem = when (this) {

    FavoritesDestination -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = drawable.ic_favorite,
    )

    DeletedDestination -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = drawable.ic_delete,
    )

    ChooseDataStructureDestination -> NavUiControllerItem(
        title = toStringResources(),
        route = route,
        iconId = drawable.ic_delete,
    )
}


@Preview
@Composable
fun MainNavigationPreview() {
    FakeWindowSizeType {
        MainNavigation()
    }
}