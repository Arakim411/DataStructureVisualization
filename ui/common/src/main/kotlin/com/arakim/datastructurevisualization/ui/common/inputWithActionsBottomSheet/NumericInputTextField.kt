package com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.arakim.datastructurevisualization.ui.common.R

@Composable
fun NumericInputTextField(
    onValueChange: (Number?) -> Unit,
    initialValue: Number? = null,
    maxValue: Number = Int.MAX_VALUE,
    minValue: Number = Int.MIN_VALUE,
    label: @Composable (() -> Unit)? = null,
) {
    var textFieldValue by rememberSaveable {
        mutableStateOf(initialValue?.toString() ?: "")
    }

    var errorMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val valueIsGreaterThanMaxMessage = stringResource(
        R.string.value_is_grater_than_max_value, maxValue.toString()
    )
    val valueIsSmallerThanMinMessage = stringResource(
        R.string.value_is_smaller_than_min_value, minValue.toString()
    )


    OutlinedTextField(
        value = textFieldValue,
        label = label,
        onValueChange = { newValue ->
            errorMessage = null
            textFieldValue = newValue
            val newValueNumeric = newValue.toDoubleOrNull()

            when {
                newValueNumeric == null -> {
                    onValueChange(null)
                }

                newValueNumeric > maxValue.toDouble() -> {
                    onValueChange(null)
                    errorMessage = valueIsGreaterThanMaxMessage
                }

                newValueNumeric < minValue.toDouble() -> {
                    onValueChange(null)
                    errorMessage = valueIsSmallerThanMinMessage
                }

                else -> {
                    onValueChange(newValueNumeric)
                }
            }
        },
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
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors()
    )
}