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
import com.arakim.datastructurevisualization.ui.common.genericpicker.R
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.NumericInputTextField
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.helpers.AcceptCancelButtons
import com.arakim.datastructurevisualization.ui.genericPicker.presenter.model.PickerDataType.DurationType
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun PickDurationView(
    durationType: DurationType,
    title: String,
    onDurationPicked: (duration: Duration) -> Unit,
    onCancel: () -> Unit,
) {
    Dialog(onDismissRequest = onCancel) {

        var durationMillis: Long? by remember { mutableStateOf(durationType.value.inWholeMilliseconds) }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)
                .padding(Dimens.PickerContainerPadding),
        ) {
            Column {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.pick_duration_title, title),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                NumericInputTextField(
                    onValueChange = { newDurationMillis ->
                        durationMillis = newDurationMillis?.toLong()
                    },
                    initialValue = durationMillis?.toInt(),
                    minValue = 0,
                    maxValue = 5.seconds.inWholeMilliseconds,
                    label = {
                        Text(text = stringResource(id = R.string.milliseconds))
                    }
                )
                AcceptCancelButtons(
                    onCancel = onCancel,
                    accept = {
                        onDurationPicked(durationMillis!!.toLong().milliseconds)
                        onCancel()
                    },
                    isAcceptEnabled = durationMillis != null,
                )
            }
        }
    }
}

