package com.arakim.datastructurevisualization.ui.genericPicker.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.ui.common.DraggableBox
import com.arakim.datastructurevisualization.ui.genericPicker.compose.floatingModalItemsView.FloatingModalItemsView
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerPresenter
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerState.IdleState
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerState.ReadyState

@Composable
fun GenericPickerView(
    optionsPickerPresenter: GenericPickerPresenter,
    content: @Composable () -> Unit
) {
    val stateValue = optionsPickerPresenter.stateFlow.collectAsStateWithLifecycle().value

    content()

    when (stateValue) {
        IdleState -> Unit
        is ReadyState -> GenericPickerReadyView(
            state = stateValue,
            onAction = optionsPickerPresenter::onAction,
        )
    }

}

@Composable
internal fun GenericPickerReadyView(
    state: ReadyState,
    onAction: (NewDataPickedAction) -> Unit,
) {

    val density = LocalDensity.current
    val startModalX = rememberSaveable { with(density) { 10.dp.toPx().toInt() } }

    val isDetailsDialogVisible = rememberSaveable { mutableStateOf(false) }

    if (isDetailsDialogVisible.value) {
        GenericPickerDetailsDialog(
            items = state.allItems,
            onDismissRequest = { isDetailsDialogVisible.value = false },
            onAction = onAction,
        )
    }

    DraggableBox(startPosition = IntOffset(startModalX, 0)) {

        FloatingModalItemsView(
            items = state.floatingModalItems,
            onMoreDetailsClick = {
                isDetailsDialogVisible.value = true
            },
            onAction = onAction,
        )
    }
}

