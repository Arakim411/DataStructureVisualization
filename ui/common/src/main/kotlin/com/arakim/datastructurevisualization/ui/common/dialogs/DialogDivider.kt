package com.arakim.datastructurevisualization.ui.common.dialogs

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ColumnScope.DialogDivider() {
    val spacerModifier = Modifier.height(DialogDimens.TitleBodyPadding / 2)
    Spacer(modifier = spacerModifier)
    Divider()
    Spacer(modifier = spacerModifier)
}