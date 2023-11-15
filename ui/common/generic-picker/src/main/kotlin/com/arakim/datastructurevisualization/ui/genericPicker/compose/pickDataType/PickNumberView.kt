package com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arakim.datastructurevisualization.ui.common.genericpicker.R.string
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.NumericInputTextField
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogBottomTextButtons


@Composable
internal fun PickNumberView(
    currentNumber: Number,
    unit: String,
    onNumberPicked: (number: Float) -> Unit,
    title: String,
    onCancel: () -> Unit,
) {
    Dialog(onDismissRequest = onCancel) {

        var number: Number? by remember { mutableStateOf(currentNumber) }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)
                .padding(Dimens.PickerContainerPadding),
        ) {
            Column {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(string.change, title),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                NumericInputTextField(
                    onValueChange = { newNumber ->
                        number = newNumber
                    },
                    initialValue = number,
                    minValue = 0.1,
                    label = {
                        Text(text = unit)
                    }
                )
                DialogBottomTextButtons(
                    onCancel = onCancel,
                    onAccept = {
                        onNumberPicked(number!!.toFloat())
                        onCancel()
                    },
                    isAcceptEnabled = number != null,
                )
            }
        }
    }
}

