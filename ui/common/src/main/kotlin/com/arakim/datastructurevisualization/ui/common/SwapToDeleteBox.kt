package com.arakim.datastructurevisualization.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun SwappableToDeleteBox(
    onDelete: () -> Unit,
    content: @Composable () -> Unit,
) {
    val deleteAction = SwipeAction(
        onSwipe = onDelete,
        icon = painterResource(R.drawable.baseline_delete_sweep_24),
        background = Color.Red.copy(alpha = 0.5f),
    )

    SwipeableActionsBox(endActions = listOf(deleteAction)) {
        content()
    }
}