package com.arakim.datastructurevisualization.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.arakim.datastructurevisualization.ui.navigation.MainNavDestinations.ChooseDataStructureDestination
import com.arakim.datastructurevisualization.ui.navigation.MainNavDestinations.DeletedDestination
import com.arakim.datastructurevisualization.ui.navigation.MainNavDestinations.FavoritesDestination


enum class MainNavDestinations(val route: String) {
    ChooseDataStructureDestination("choose_data_structure"),
    FavoritesDestination("favorites"),
    DeletedDestination("deleted")
}

@Composable
fun MainNavDestinations.toStringResources(): String = when (this) {
    ChooseDataStructureDestination -> stringResource(id = R.string.destination_name_data_structure)
    FavoritesDestination -> stringResource(id = R.string.destination_name_favorite)
    DeletedDestination -> stringResource(id = R.string.destination_name_delete)
}