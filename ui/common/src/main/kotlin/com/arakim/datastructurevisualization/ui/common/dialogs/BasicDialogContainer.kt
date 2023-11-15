package com.arakim.datastructurevisualization.ui.common.dialogs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CommonDialogContainer(
    content: @Composable () -> Unit,
) {

    DialogDimens.Container.apply {
        Surface(
            modifier = Modifier
                .widthIn(
                    min = mindWidth,
                    max = maxWidth,
                ),
            shape = MaterialTheme.shapes.extraLarge,
            shadowElevation = elevation,
        ) {
            Box(modifier = Modifier.padding(padding)) {
                content()
            }
        }
    }
}