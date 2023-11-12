package com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.arakim.datastructurevisualization.ui.common.genericpicker.R.string
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.helpers.AcceptCancelButtons
import com.godaddy.android.colorpicker.ClassicColorPicker
import com.godaddy.android.colorpicker.HsvColor

@Composable
internal fun PickColorView(
    color: Color,
    title: String,
    onNewColorPicked: (Color) -> Unit,
    onCancel: () -> Unit
) {
    Dialog(onDismissRequest = onCancel) {
        val currentColor = remember { mutableStateOf(HsvColor.from(color)) }
        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.shapes.medium,
                )
                .padding(Dimens.PickerContainerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ClassicColorPicker(
                modifier = Modifier.size(Dimens.ColorPickerSize),
                color = currentColor.value,
                showAlphaBar = true,
                onColorChanged = { newColor ->
                    currentColor.value = newColor
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            AcceptCancelButtons(
                onCancel = onCancel,
                accept = {
                    onNewColorPicked(currentColor.value.toColor())
                    onCancel()
                },
                isAcceptEnabled = true,
            )
        }
    }
}

@Composable
internal fun ColorView(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .width(Dimens.TypeViewHeight)
            .height(Dimens.TypeViewHeight)
            .background(color, CircleShape),
        contentAlignment = Alignment.CenterEnd,
    ) {}
}
