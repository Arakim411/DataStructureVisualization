package com.arakim.datastructurevisualization.ui.genericPicker.compose

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerPresenter
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerState.IdleState
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerState.ReadyState

@Composable
fun GenericPickerView(
    optionsPickerPresenter: GenericPickerPresenter,
    content: @Composable () -> Unit
) {
    val state = optionsPickerPresenter.stateFlow.collectAsStateWithLifecycle()

    content()

    Crossfade(targetState = state.value, label = "") { stateValue ->
        when (stateValue) {
            IdleState -> Unit
            is ReadyState -> GenericPickerReadyView(
                state = stateValue,
                onAction = optionsPickerPresenter::onAction,
            )
        }
    }
}

@Composable
internal fun GenericPickerReadyView(
    state: ReadyState,
    onAction: (NewDataPickedAction) -> Unit,
) {

    val isDetailsDialogVisible = remember { mutableStateOf(true) }

    if (isDetailsDialogVisible.value) {
        GenericPickerDetailsDialog(
            items = state.allItems,
            onDismissRequest = { isDetailsDialogVisible.value = false },
            onAction = onAction,
        )
    }

    Button(
        onClick = { isDetailsDialogVisible.value = true }
    ) {
        Text(text = "open")
    }
}

