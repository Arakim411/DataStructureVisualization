package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonErrorView
import com.arakim.datastructurevisualization.ui.common.CommonLoaderView
import com.arakim.datastructurevisualization.ui.common.CommonTopAppBar
import com.arakim.datastructurevisualization.ui.common.dialogs.CommonDialogContainer
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogBottomTextButtons
import com.arakim.datastructurevisualization.ui.common.list.CommonListItem
import com.arakim.datastructurevisualization.ui.common.list.ListDimens.MainContainer
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.DeletedDataStructureViewModel
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction.StopDeletionProcessAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureSideEffect.StoppedDeletionProcess
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.ErrorState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.IdleState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.InitializingState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.ReadyState
import com.arakim.datastructurevisualization.ui.screens.deleteddatastructures.R
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun DeletedDataStructureScreen(
    navUiControllerState: NavigationUiControllerState,
) {

    val viewModel = hiltViewModel<DeletedDataStructureViewModel>()
    val state = viewModel.presenter.stateFlow.collectAsStateWithLifecycle().value

    val context = LocalContext.current
    val snackBarState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.presenter.sideEffectFlow.onEach {
            when (it) {
                is StoppedDeletionProcess -> {
                    snackBarState.showSnackbar(
                        context.getString(
                            R.string.stopped_deletion_process_for,
                            it.dataStructureName,
                        )
                    )
                }
            }
        }.launchIn(this)
    }
    LaunchedEffect(Unit) {
        viewModel.presenter.onAction(InitializeAction)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarState) },
        topBar = {
            CommonTopAppBar(
                title = stringResource(id = R.string.deleted_data_structures_screen_title),
                navigationUiControllerState = navUiControllerState,
            )

        },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Crossfade(targetState = state, label = "") { stateValue ->
                when (stateValue) {
                    ErrorState -> CommonErrorView()
                    IdleState -> Unit
                    InitializingState -> CommonLoaderView()
                    is ReadyState -> ReadyStateView(stateValue, onAction = viewModel.presenter::onAction)
                }
            }
        }
    }

}

@Composable
private fun ReadyStateView(
    state: ReadyState,
    onAction: (DeletedDataStructureAction) -> Unit,
) {
    val confirmStopDeletionProcess = remember { mutableStateOf<DataStructureUiModel?>(null) }

    confirmStopDeletionProcess.value?.also {
        ConfirmStopDeletionDialogDialog(
            dataStructure = it,
            onConfirm = { onAction(StopDeletionProcessAction(it)) },
            onDismissRequest = {
                confirmStopDeletionProcess.value = null
            },
        )
    }

    if (state.dataStructures.isEmpty()) {
        NoDeletedDataStructuresView()
    } else {
        DeletedDataStructureView(
            dataStructures = state.dataStructures,
            onRecoverClick = {
                confirmStopDeletionProcess.value = it
            },
        )
    }
}


@Composable
private fun ConfirmStopDeletionDialogDialog(
    dataStructure: DataStructureUiModel,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    Dialog(onDismissRequest = onDismissRequest) {
        CommonDialogContainer {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(stringResource(id = R.string.confirm_stop_deletion_process_message).plus(" "))
                    Text(text = "\"${dataStructure.customName}\"".plus(" "), fontWeight = FontWeight.Bold)
                    Text(text = "?")
                }
                DialogBottomTextButtons(
                    onCancel = onDismissRequest,
                    onAccept = {
                        onConfirm()
                        onDismissRequest()
                    },
                    isAcceptEnabled = true,
                )
            }
        }
    }
}

@Composable
private fun NoDeletedDataStructuresView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "empty")
    }
}

@Composable
private fun DeletedDataStructureView(
    dataStructures: ImmutableList<DataStructureUiModel>,
    onRecoverClick: (DataStructureUiModel) -> Unit,
) {
    Column {
        dataStructures.forEach { item ->
            Column {
                CommonListItem(
                    modifier = Modifier,
                    headLine = item.customName.plus(" ${item.deletionDate}"),
                    leadingIcon = item.dataStructureType.iconResId,
                    supportingText = item.dataStructureType.name,
                    trailingIcon = R.drawable.baseline_restore_24,
                    onTrailingIconClick = { onRecoverClick(item) },
                )
                DeletionDate(date = item.deletionDate.toString())
            }
            Spacer(modifier = Modifier.height(4.dp))
            Divider()
        }
    }
}

@Composable
private fun DeletionDate(date: String) {
    Box(
        modifier = Modifier
            .padding(start = MainContainer.HorizontalPadding)
            .fillMaxWidth()
    ) {
        Row {
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(MainContainer.LeadingTextMargin))
            Text(text = date, fontSize = 12.sp)
        }
    }
}

