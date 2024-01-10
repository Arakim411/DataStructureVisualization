package com.arakim.datastructurevisualization.ui.common.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.arakim.datastructurevisualization.ui.common.R
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.NumericInputTextField

@Composable
fun CommonInputDialog(
    onDismissRequest: () -> Unit,
    onConfirmed: (number: Number) -> Unit,
    inputLabel: String,

    ) {
    var currentValue: Number? by rememberSaveable { mutableStateOf(null) }

    Dialog(onDismissRequest = onDismissRequest) {
        CommonDialogContainer {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                NumericInputTextField(
                    minValue = 1,
                    onValueChange = {
                        currentValue = it
                    },
                    label = { Text(inputLabel) },
                )

                Button(
                    enabled = currentValue != null,
                    onClick = {
                        onDismissRequest()
                        onConfirmed(currentValue!!)
                    },
                ) {
                    Text(text = stringResource(id = R.string.common_input_dialog_btn_text))
                }
            }
        }
    }
}