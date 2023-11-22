package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.BinarySearchTreeViewModel
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.InitializationAction.InitializeAction

@Composable
fun BinarySearchTreeScreen(
    id: Int,
    navigationUiControllerState: NavigationUiControllerState,
) {
    val viewModel = hiltViewModel<BinarySearchTreeViewModel>()

    LaunchedEffect(id){
        viewModel.presenter.onAction(InitializeAction(id))
    }

    BinarySearchTreeView(
        presenter = viewModel.presenter,
        navigationUiControllerState = navigationUiControllerState,
    )
}
