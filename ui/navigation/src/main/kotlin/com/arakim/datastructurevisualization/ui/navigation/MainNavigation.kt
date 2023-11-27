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
import com.arakim.datastructurevisualization.ui.navigation.R.string
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.BinarySearchTreeDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.ChooseDataStructureDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.DeletedDataStructuresDestination
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.ChooseDataStructureScreen
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.compose.BinarySearchTreeScreen
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.FakeWindowSizeType

//TODO add intent filters
@Composable
fun MainNavigation() {

    val navController = rememberNavController()
    val navUiController = rememberNavUiControllerState()

    fun navigate(destination: MainDestination) {
        navController.navigate(destination.navigateRoute)
    }

    NavigationUiController(
        navigationUiControllerState = navUiController,
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
                    navUiControllerState = navUiController,
                    navigate = ::navigate
                )
            }

            composable(BinarySearchTreeDestination.Route) {
                val id = it.arguments!!.getString(BinarySearchTreeDestination.Arguments.Id)!!
                BinarySearchTreeScreen(
                    id = id.toInt(),
                    navigationUiControllerState = navUiController
                )
            }

            composable(DeletedDataStructuresDestination.Route) {
                TODO()
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
}


@Preview
@Composable
fun MainNavigationPreview() {
    FakeWindowSizeType {
        MainNavigation()
    }
}