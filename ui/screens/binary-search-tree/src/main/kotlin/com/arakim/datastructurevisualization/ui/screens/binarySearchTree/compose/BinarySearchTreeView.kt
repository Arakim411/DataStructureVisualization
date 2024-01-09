package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.compose

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonErrorView
import com.arakim.datastructurevisualization.ui.common.CommonLoaderView
import com.arakim.datastructurevisualization.ui.common.CommonTopAppBar
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.InputModalAction
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.InputModalBottomSheet
import com.arakim.datastructurevisualization.ui.common.topbar.DropDownAction
import com.arakim.datastructurevisualization.ui.common.topbar.SaveDataStructureAction
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
            IdleState, InitializingState -> CommonLoaderView()
            ErrorState -> CommonErrorView()
            is ReadyState -> ReadyState(
                state = stateValue,
                visualizationBuilder = presenter.treeVisualizationBuilder.visualizationBuilder,
                navigationUiControllerState = navigationUiControllerState,
                onAction = presenter::onAction,
            )
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
    val context = LocalContext.current
    val actionsInQueue = state.actionsInQueue.collectAsStateWithLifecycle().value

    var isAddNodeBottomSheetVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isAddRandomNodesDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }

    if (isAddNodeBottomSheetVisible) {
        InputModalBottomSheet(
            actions = remember {
                immutableListOf(
                    InputModalAction(string.insert) { onAction(InsertAction(it)) },
                    InputModalAction(string.delete) { onAction(DeleteAction(it)) },
                    InputModalAction(string.find) { onAction(FindAction(it)) },
                )
            },
            onDismissRequest = { isAddNodeBottomSheetVisible = false },
            label = stringResource(id = string.bottom_sheet_node_value),
        )
    }

    if (isAddRandomNodesDialogVisible) {
        AddRandomNodesDialog(
            onDismissRequest = { isAddRandomNodesDialogVisible = false },
            onAction = onAction,
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
                    DropDownAction {
                        addAction(
                            text = context.getString(R.string.add_random_nodes_drop_down_item_text),
                            action = { isAddRandomNodesDialogVisible = true },
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isAddNodeBottomSheetVisible = true
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
            if (actionsInQueue > 0) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(bottom = 16.dp, start = 16.dp),
                    text = "Actions in queue: $actionsInQueue",
                    color = Color.Black,
                    fontSize = 12.sp
                )
            }
        }
    }

    if (!state.isTreeCreated.value) {
        CommonLoaderView()
    }
}
