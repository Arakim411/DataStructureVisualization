package com.arakim.datastructurevisualization.ui.util.views

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun TransformableBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    var scale by rememberSaveable { mutableFloatStateOf(1f) }
    var offset by rememberSaveable(saver = offsetSaver) { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .transformable(
                state = rememberTransformableState { zoomChange, offsetChange, _ ->
                    scale *= zoomChange
                    offset += offsetChange
                },
            ),
    )
    {
        Box(
            modifier = Modifier.graphicsLayer {
                translationX = offset.x; translationY = offset.y
                scaleX = scale; scaleY = scale
            },
        ) {
            content()
        }
    }
}

private val offsetSaver = run {
    mapSaver(
        save = {
               mapOf("x" to it.value.x, "y" to it.value.y)
        },
        restore = {
            mutableStateOf(Offset(it["x"] as Float, it["y"] as Float))
        }
    )
}