package com.datastructurevisualization.ui.screen.hashmap.compose

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
import androidx.compose.runtime.collectAsState
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
import com.arakim.datastructurevisualization.ui.common.dialogs.CommonInputDialog
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.InputModalAction
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.InputModalBottomSheet
import com.arakim.datastructurevisualization.ui.common.topbar.DropDownAction
import com.arakim.datastructurevisualization.ui.common.topbar.SaveDataStructureAction
import com.arakim.datastructurevisualization.ui.screens.hashmap.R.string
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.visualizationbuilder.compose.VisualizationBuilderView
import com.arakim.datastructurevisualization.ui.visualizationbuilder.presenter.VisualizationBuilder
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.SaveAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.UpdateAction.AddRandomValuesAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.UpdateAction.DeleteAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapAction.UpdateAction.InsertAction
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapPresenter
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapSideEffect.SavedSideEffect
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ErrorState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.IdleState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.InitializingState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ReadyState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun HashMapView(
    presenter: HashMapPresenter,
    navigationUiControllerState: NavigationUiControllerState,
) {
    val state = presenter.stateFlow.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        presenter.sideEffectFlow.onEach { sideEffect ->
            when (sideEffect) {
                SavedSideEffect -> {
                    Toast.makeText(
                        context,
                        com.arakim.datastructurevisualization.ui.common.R.string.info_data_structure_saved,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }.launchIn(this)
    }

    Crossfade(targetState = state.value, label = "") { stateValue ->
        when (stateValue) {
            ErrorState -> CommonErrorView()
            IdleState, InitializingState -> CommonLoaderView()
            is ReadyState -> ReadyState(
                state = stateValue,
                visualizationBuilder = presenter.hashMapVisualizationBuilder.visualizationBuilder,
                navigationUiControllerState = navigationUiControllerState,
                onAction = { presenter.onAction(it) },
            )
        }
    }
}

@Composable
private fun ReadyState(
    state: ReadyState,
    visualizationBuilder: VisualizationBuilder,
    navigationUiControllerState: NavigationUiControllerState,
    onAction: (HashMapAction) -> Unit,
) {
    val context = LocalContext.current
    val actionsInQueue = state.actionsInQueue.collectAsStateWithLifecycle().value

    var isAddValueBottomSheetVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var isAddRandomValuesDialogVisible by rememberSaveable {
        mutableStateOf(false)
    }

    if (isAddValueBottomSheetVisible) {
        InputModalBottomSheet(
            actions = remember {
                immutableListOf(
                    InputModalAction(string.insert) { onAction(InsertAction(it)) },
                    InputModalAction(string.delete) { onAction(DeleteAction(it)) },
                )
            },
            onDismissRequest = { isAddValueBottomSheetVisible = false },
            label = stringResource(id = string.bottom_sheet_label),
        )
    }

    if (isAddRandomValuesDialogVisible) {
        CommonInputDialog(
            onDismissRequest = { isAddRandomValuesDialogVisible = false },
            inputLabel = stringResource(id = string.add_random_values_drop_down_item_text),
            onConfirmed = { onAction(AddRandomValuesAction(it.toInt())) },
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
                            text = context.getString(string.add_random_values_drop_down_item_text),
                            action = { isAddRandomValuesDialogVisible = true },
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isAddValueBottomSheetVisible = true
                },
            ) {
                Icon(
                    painter = painterResource(
                        id = com.arakim.datastructurevisualization.ui.common.R.drawable.baseline_edit_24,
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

    if (!state.isHashMapCreated.value) {
        CommonLoaderView()
    }

}