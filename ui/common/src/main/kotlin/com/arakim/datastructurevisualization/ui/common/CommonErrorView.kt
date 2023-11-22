package com.arakim.datastructurevisualization.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun CommonErrorView() {
    val textColor = MaterialTheme.colorScheme.error
    val style = MaterialTheme.typography.titleMedium.copy(color = textColor)

    Box(
        modifier = Modifier.fillMaxSize(),
        Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.common_error_view_title),
            style = style
        )
    }
}