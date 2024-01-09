package com.datastructurevisualization.ui.screen.hashmap.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.datastructurevisualization.ui.screen.hashmap.HashMapViewModel
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.InitializationAction.InitializeAction

@Composable
fun HashMapScreen(
    id: Int,
    navigationUiControllerState: NavigationUiControllerState,
) {

    val viewModel = hiltViewModel<HashMapViewModel>()

    LaunchedEffect(id) {
        viewModel.presenter.onAction(InitializeAction(id))
    }

    HashMapView(
        presenter = viewModel.presenter,
        navigationUiControllerState = navigationUiControllerState,
    )

}