package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.compose

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonErrorView
import com.arakim.datastructurevisualization.ui.common.CommonLoaderView
import com.arakim.datastructurevisualization.ui.common.CommonTopAppBar
import com.arakim.datastructurevisualization.ui.common.SaveDataStructureAction
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.InputModalAction
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.InputModalBottomSheet
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.SaveAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.DeleteAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.FindAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.InsertAction
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreePresenter
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeSideEffect.SavedSideEffect
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.ErrorState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.IdleState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.InitializingState
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeState.ReadyState
import com.arakim.datastructurevisualization.ui.screens.binarysearchtree.R
import com.arakim.datastructurevisualization.ui.screens.binarysearchtree.R.string
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.compose.VisualizationBuilderView
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun BinarySearchTreeView(
    presenter: BinarySearchTreePresenter,
    navigationUiControllerState: NavigationUiControllerState
) {
    val state = presenter.stateFlow.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        presenter.sideEffectFlow.onEach { sideEffect ->
            when (sideEffect) {
                SavedSideEffect -> {
                    Toast.makeText(context, string.info_data_structure_saved, Toast.LENGTH_SHORT).show()
                }
            }
        }.launchIn(this)
    }

    Crossfade(
        targetState = state.value,
        label = "",
    ) { stateValue ->
        when (stateValue) {
            is ReadyState -> ReadyState(
                state = stateValue,
                visualizationBuilder = presenter.treeVisualizationBuilder.visualizationBuilder,
                navigationUiControllerState = navigationUiControllerState,
                onAction = presenter::onAction,
            )

            IdleState -> Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
            ErrorState -> CommonErrorView()
            InitializingState -> CommonLoaderView()
        }
    }
}

@Composable
private fun ReadyState(
    state: ReadyState,
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
            label = stringResource(id = R.string.bottom_sheet_node_value),
        )
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = state.customName,
                navigationUiControllerState = navigationUiControllerState,
                actions = {
                    SaveDataStructureAction(
                        onClick = {
                            onAction(SaveAction)
                        }
                    )
                }
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
            VisualizationBuilderView(visualizationPresenter = visualizationBuilder)
        }
    }
}
