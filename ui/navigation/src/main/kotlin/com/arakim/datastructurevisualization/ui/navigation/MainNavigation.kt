package com.arakim.datastructurevisualization.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiController
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerGroup
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerItem
import com.arakim.datastructurevisualization.navigation.uicontroller.rememberNavUiControllerState
import com.arakim.datastructurevisualization.ui.navigation.R.drawable
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.ChooseDataStructureDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.DeletedDataStructuresDestination
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.ChooseDataStructureScreen
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
            startDestination = ChooseDataStructureDestination.Route
        ) {

            composable(ChooseDataStructureDestination.Route) {
                ChooseDataStructureScreen(navUiControllerState = navUiController)
            }

            composable(DeletedDataStructuresDestination.Route) {
                TODO()
            }
        }
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
        DeletedDataStructuresDestination.toNavItem(),
    ),
)

//TODO correct icons
@Composable
@Stable
private fun MainDestination.toNavItem(): NavUiControllerItem = when (this) {


    DeletedDataStructuresDestination -> NavUiControllerItem(
        title = toStringResources(),
        route = DeletedDataStructuresDestination.Route,
        iconId = drawable.ic_delete,
    )

    ChooseDataStructureDestination -> NavUiControllerItem(
        title = toStringResources(),
        route = ChooseDataStructureDestination.Route,
        iconId = drawable.ic_stack,
    )
}

@Composable
private fun MainDestination.toStringResources(): String = when (this) {
    DeletedDataStructuresDestination -> stringResource(id = R.string.destination_name_delete)
    ChooseDataStructureDestination -> stringResource(id = R.string.destination_name_data_structure)
}


@Preview
@Composable
fun MainNavigationPreview() {
    FakeWindowSizeType {
        MainNavigation()
    }
}