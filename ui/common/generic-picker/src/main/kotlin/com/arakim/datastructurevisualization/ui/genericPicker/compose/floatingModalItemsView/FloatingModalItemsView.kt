package com.arakim.datastructurevisualization.ui.genericPicker.compose.floatingModalItemsView

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.helpers.PickDataTypeDialog
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.GenericPickerAction.NewDataPickedAction
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.GenericPickerItem
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.ImpactProperty.Height
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.ImpactProperty.Width
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeDelimiter
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Compact
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Expanded
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Medium

@Composable
internal fun FloatingModalItemsView(
    items: ImmutableList<GenericPickerItem<*>>,
    onAction: (NewDataPickedAction) -> Unit,
    onMoreDetailsClick: () -> Unit,
) {
    val isExpanded = rememberSaveable { mutableStateOf(true) }
    var currentlyPickingItem: GenericPickerItem<*>? by remember { mutableStateOf(null) }

    currentlyPickingItem?.also {
        PickDataTypeDialog(
            item = it,
            onAction = onAction,
            onDismissRequest = { currentlyPickingItem = null },
        )
    }

    WindowSizeDelimiter(impactedProperty = Width) { windowSizeType ->
        when (windowSizeType) {

            Compact, Medium -> FloatingModalItemsCompactMedium(
                items = items,
                onMoreDetailsClick = onMoreDetailsClick,
                onItemClick = { currentlyPickingItem = it },
                isExpanded = isExpanded,
            )

            Expanded -> FloatingModalItemsExpanded(
                items = items,
                onMoreDetailsClick = onMoreDetailsClick,
                onItemClick = { currentlyPickingItem = it },
                isExpanded = isExpanded,
            )
        }

    }

}


