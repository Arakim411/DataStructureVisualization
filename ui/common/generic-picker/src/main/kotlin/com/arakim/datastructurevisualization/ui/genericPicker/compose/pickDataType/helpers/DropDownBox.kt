package com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.helpers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.common.genericpicker.R.drawable
import com.arakim.datastructurevisualization.ui.genericPicker.compose.pickDataType.Dimens

@Composable
fun DropDownBox(
    modifier: Modifier = Modifier,
    text: String,
) {

    Box(
        modifier = modifier.background(
            MaterialTheme.colorScheme.inverseOnSurface,
            MaterialTheme.shapes.small
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .width(Dimens.TypeViewWidth)
                .height(Dimens.TypeViewHeight),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                painter = painterResource(id = drawable.baseline_arrow_drop_down_24),
                contentDescription = null
            )

        }
    }
}