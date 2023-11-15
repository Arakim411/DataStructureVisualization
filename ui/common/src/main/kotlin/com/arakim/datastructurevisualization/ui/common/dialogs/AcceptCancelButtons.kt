package com.arakim.datastructurevisualization.ui.common.dialogs

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.common.R

@Composable
fun ColumnScope.DialogBottomTextButtons(
    onCancel: () -> Unit,
    onAccept: () -> Unit,
    overrideCancelText: String? = null,
    overrideAcceptText: String? = null,
    isAcceptEnabled: Boolean,
) {
    val cancelText = overrideCancelText ?: stringResource(id = R.string.dialog_cancel_title)
    val acceptText = overrideAcceptText ?: stringResource(id = R.string.dialog_accept_title)

    Row(modifier = Modifier.align(Alignment.End)) {
        TextButton(onClick = { onCancel() }) {
            Text(
                text = cancelText,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        TextButton(
            onClick = onAccept,
            enabled = isAcceptEnabled
        ) {
            Text(
                text = acceptText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
