package com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputModalBottomSheet(
    actions: ImmutableList<InputModalAction>,
    onDismissRequest: () -> Unit,
) {

    var textFieldValue by rememberSaveable {
        mutableStateOf("")
    }
    var errorMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    fun getNumberOrShowError(): Number? =
        textFieldValue.toDoubleOrNull() ?: run {
            errorMessage = "Provides numeric value"
            null
        }

    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            InputTextField(
                value = textFieldValue,
                onValueChange = {
                    errorMessage = null
                    textFieldValue = it
                },
                errorMessage = errorMessage,
            )

            ActionButtons(
                actions = actions,
                getCurrentNumberIfValid = ::getNumberOrShowError,
                onDismissRequest = onDismissRequest,
            )

            // TODO handle like grid for more 4+ actions

        }
    }
}

@Composable
private fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String?
) {
    OutlinedTextField(
        value = value,
        label = { Text(text = "Value") },
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            autoCorrect = false,
            keyboardType = KeyboardType.Number,
        ),
        supportingText = {
            errorMessage?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                )

            }
        },
        isError = errorMessage != null,
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors()
    )
}


@Composable
private fun ActionButtons(
    actions: ImmutableList<InputModalAction>,
    getCurrentNumberIfValid: () -> Number?,
    onDismissRequest: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        actions.forEach { item ->
            OutlinedButton(
                modifier = Modifier.padding(horizontal = 8.dp),
                onClick = {
                    getCurrentNumberIfValid()?.also { number ->
                        item.onClick(number)
                        onDismissRequest()
                    }
                },
            ) {
                Text(text = stringResource(id = item.titleResId))
            }
        }
    }
}


