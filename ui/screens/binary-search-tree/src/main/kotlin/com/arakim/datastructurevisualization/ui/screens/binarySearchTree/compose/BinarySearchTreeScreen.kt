package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.navigation.uicontroller.rememberNavUiControllerState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.BinarySearchTreeViewModel
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.FakeWindowSizeType

@Composable
fun BinarySearchTreeScreen(
    navigationUiControllerState: NavigationUiControllerState,
) {
    val viewModel = hiltViewModel<BinarySearchTreeViewModel>()

    BinarySearchTreeView(
        presenter = viewModel.presenter,
        navigationUiControllerState = navigationUiControllerState,
    )
}


@Preview
@Composable
fun BinarySearchTreeScreenPreview() {
    FakeWindowSizeType {
        BinarySearchTreeScreen(
            navigationUiControllerState = rememberNavUiControllerState(),
        )
    }
}