package com.arakim.datastructurevisualization.ui.common.dialogs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DialogHeadline(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineSmall,
    )
}