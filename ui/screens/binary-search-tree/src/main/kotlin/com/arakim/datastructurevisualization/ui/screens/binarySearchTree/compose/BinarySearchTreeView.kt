package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.compose

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonTopAppBar
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.InputModalAction
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.InputModalBottomSheet
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.DeleteAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.FindAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.InsertAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreePresenter
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState
import com.arakim.datastructurevisualization.ui.screens.binarysearchtree.R
import com.arakim.datastructurevisualization.ui.screens.binarysearchtree.R.string
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.compose.VisualizationBuilder
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.Random.Default

@Composable
fun BinarySearchTreeView(
    presenter: BinarySearchTreePresenter,
    navigationUiControllerState: NavigationUiControllerState
) {
    val state = presenter.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        presenter.treeVisualizationBuilder.insert(0)

        val values = mutableListOf<Int>()
        for(i in 0 until 30){
            val random = Random.nextInt(from = -200, until = 200)
            values.add(random)
            presenter.treeVisualizationBuilder.insert(random)
        }
    }

    Crossfade(
        targetState = state.value,
        label = "",
    ) { stateValue ->
        when (stateValue) {
            BinarySearchTreeState.ReadyState -> ReadyState(
                visualizationBuilder = presenter.treeVisualizationBuilder.visualizationBuilder,
                navigationUiControllerState = navigationUiControllerState,
                onAction = presenter::onAction,
            )
        }
    }
}

@Composable
private fun ReadyState(
    visualizationBuilder: VisualizationBuilder,
    navigationUiControllerState: NavigationUiControllerState,
    onAction: (BinarySearchTreeAction) -> Unit,
) {
    var isBottomSheetVisible by rememberSaveable {
        mutableStateOf(false)
    }

    if (isBottomSheetVisible) {
        InputModalBottomSheet(
            actions = remember {
                immutableListOf(
                    InputModalAction(string.insert) { onAction(InsertAction(it)) },
                    InputModalAction(string.delete) { onAction(DeleteAction(it)) },
                    InputModalAction(string.find) { onAction(FindAction(it)) },
                )
            },
            onDismissRequest = { isBottomSheetVisible = false },
        )
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = stringResource(id = R.string.binary_search_tree_screen_title),
                navigationUiControllerState = navigationUiControllerState,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isBottomSheetVisible = true
                },
            ) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.baseline_edit_24,
                    ),
                    contentDescription = null
                )
            }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            VisualizationBuilder(visualizationPresenter = visualizationBuilder)
        }
    }
}
