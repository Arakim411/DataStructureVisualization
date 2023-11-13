package com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.helpers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arakim.datastructurevisualization.ui.common.genericpicker.R.drawable
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.Dimens

@Composable
fun TextDropDownBox(
    modifier: Modifier = Modifier,
    text: String,
) {

    DropDownBox(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun DropDownBox(
    modifier: Modifier,
    contentAlignment: Alignment,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier.background(
            MaterialTheme.colorScheme.inverseOnSurface,
            MaterialTheme.shapes.small
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp)
                .width(Dimens.TypeViewWidth)
                .height(Dimens.TypeViewHeight),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = contentAlignment,
            ) {
                content()
            }
            Icon(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = drawable.baseline_arrow_drop_down_24),
                contentDescription = null
            )

        }
    }
}