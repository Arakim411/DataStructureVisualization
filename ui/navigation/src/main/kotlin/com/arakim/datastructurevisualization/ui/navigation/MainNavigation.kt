package com.arakim.datastructurevisualization.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiController
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.navigation.uicontroller.ObtainUiControllerState
import com.arakim.datastructurevisualization.navigation.uicontroller.UiControllerType
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerGroup
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerItem
import com.arakim.datastructurevisualization.navigation.uicontroller.rememberModalDrawerState
import com.arakim.datastructurevisualization.ui.navigation.R.drawable
import com.arakim.datastructurevisualization.ui.navigation.R.string
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.BinarySearchTreeDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.ChooseDataStructureDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.DeletedDataStructuresDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.HashMapDestination
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.ChooseDataStructureScreen
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.compose.BinarySearchTreeScreen
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.FakeWindowSizeType
import com.datastructurevisualization.ui.screen.hashmap.compose.HashMapScreen

//TODO add intent filters
@Composable
fun MainNavigation() {

    val navController = rememberNavController()

    fun navigate(destination: MainDestination) {
        navController.navigate(destination.navigateRoute)
    }

    ObtainUiControllerState { uiControllerState ->

        forceModalUiControllerIfNeeded(navController, uiControllerState)

        NavigationUiController(
            navigationUiControllerState = uiControllerState,
            navUiControllerGroups = immutableListOf(getNavControllerGeneralGroup()),
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
                    ChooseDataStructureScreen(
                        navUiControllerState = uiControllerState,
                        navigate = ::navigate
                    )
                }

                composable(BinarySearchTreeDestination.Route) {
                    val id = it.arguments!!.getString(BinarySearchTreeDestination.Arguments.Id)!!
                    BinarySearchTreeScreen(
                        id = id.toInt(),
                        navigationUiControllerState = uiControllerState
                    )
                }

                composable(HashMapDestination.Route) {
                    val id = it.arguments!!.getString(HashMapDestination.Arguments.Id)!!
                    HashMapScreen(
                        id = id.toInt(),
                        navigationUiControllerState = uiControllerState
                    )
                }

                composable(DeletedDataStructuresDestination.Route) {
                    TODO()
                }
            }
        }
    }
}

@Composable
@Stable
private fun getNavControllerGeneralGroup(): NavUiControllerGroup = NavUiControllerGroup(
    name = stringResource(id = R.string.destination_group_general),
    items = immutableListOf(
        NavUiControllerItem(
            title = ChooseDataStructureDestination.toStringResources(),
            route = ChooseDataStructureDestination.Route,
            iconId = drawable.ic_stack,
        ),
        NavUiControllerItem(
            title = DeletedDataStructuresDestination.toStringResources(),
            route = DeletedDataStructuresDestination.Route,
            iconId = drawable.ic_delete,
        ),
    )
)

@Composable
private fun MainDestination.toStringResources(): String = when (this) {
    DeletedDataStructuresDestination -> stringResource(id = string.destination_name_delete)
    ChooseDataStructureDestination -> stringResource(id = string.destination_name_data_structure)
    is BinarySearchTreeDestination -> stringResource(id = string.destination_name_binary_search_tree)
    is HashMapDestination -> stringResource(id = string.destination_name_hash_table)
}

@Composable
private fun forceModalUiControllerIfNeeded(
    navController: NavController,
    uiControllerState: NavigationUiControllerState,
) {
    val modalDrawer = rememberModalDrawerState()
    var lastControllerType = remember<UiControllerType?> { null }

    LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (shouldForceModalUiController(destination.route)) {
                lastControllerType = uiControllerState.navigationType.value
                uiControllerState.forceUiControllerType(modalDrawer)
            } else {
                lastControllerType?.let {
                    uiControllerState.forceUiControllerType(it)
                }
            }
        }
    }
}

@Stable
private fun shouldForceModalUiController(currentRoute: String?): Boolean = when (currentRoute) {
    BinarySearchTreeDestination.Route -> true
    else -> false
}

@Preview
@Composable
fun MainNavigationPreview() {
    FakeWindowSizeType {
        MainNavigation()
    }
}