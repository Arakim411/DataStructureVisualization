package com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType

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
import com.arakim.datastructurevisualization.ui.common.genericpicker.R.string

@Composable
internal fun ColumnScope.AcceptCancelButtons(
    onCancel: () -> Unit,
    accept: () -> Unit,
    isAcceptEnabled: Boolean,
) {
    Row(modifier = Modifier.align(Alignment.End)) {
        TextButton(onClick = { onCancel() }) {
            Text(text = stringResource(id = string.picker_cancel_title))
        }

        Spacer(modifier = Modifier.width(8.dp))

        TextButton(
            onClick = accept,
            enabled = isAcceptEnabled
        ) {
            Text(
                text = stringResource(string.picker_accept_title),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
