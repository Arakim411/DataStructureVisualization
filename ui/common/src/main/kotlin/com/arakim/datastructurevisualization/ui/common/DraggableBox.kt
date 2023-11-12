package com.arakim.datastructurevisualization.ui.common

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset

@Composable
fun DraggableBox(
    startPosition: IntOffset,
    content: @Composable () -> Unit,
) {
    val position = remember { mutableStateOf(startPosition) }

    Box(
        modifier = Modifier
            .offset { position.value }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    position.value += IntOffset(dragAmount.x.toInt(), dragAmount.y.toInt())
                }
            },
    ) {
        content()
    }
}