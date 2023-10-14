package com.arakim.datastructurevisualization.ui.screens.binarySearchTree

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.navigation.uicontroller.rememberNavUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonTopAppBar
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.FakeWindowSizeType

@Composable
fun BinarySearchTreeScreen(
    navigationUiControllerState: NavigationUiControllerState
) {

    Column {
        CommonTopAppBar(
            title = "Binary Search Tree",
            navigationUiControllerState = navigationUiControllerState,
        )
        Scaffold { innerPadding ->

            LazyColumn(
                modifier = Modifier.padding(horizontal = 24.dp),
                contentPadding = innerPadding,
            ) {
                items(100) {
                    Text(text = "item: $it")
                }
            }
        }
    }
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