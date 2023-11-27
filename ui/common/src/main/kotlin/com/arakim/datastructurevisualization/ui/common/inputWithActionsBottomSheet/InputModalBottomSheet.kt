package com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputModalBottomSheet(
    actions: ImmutableList<InputModalAction>,
    onDismissRequest: () -> Unit,
    label: String?,
) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val density = LocalDensity.current
            val bottomPadding = WindowInsets.navigationBars.getBottom(density).dp
            var currentValue: Number? by rememberSaveable { mutableStateOf(null) }

            NumericInputTextField(
                onValueChange = {
                    currentValue = it
                },
                label = label?.let { { Text(text = it) } },
            )
            // TODO handle like grid for more 4+ actions
            ActionButtons(
                actions = actions,
                getCurrentNumber = { currentValue!! },
                onDismissRequest = onDismissRequest,
                areButtonsEnabled = currentValue != null,
            )
            Spacer(modifier = Modifier.height(bottomPadding))

        }
    }
}


@Composable
private fun ActionButtons(
    actions: ImmutableList<InputModalAction>,
    getCurrentNumber: () -> Number,
    areButtonsEnabled: Boolean,
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
                    item.onClick(getCurrentNumber())
                    onDismissRequest()

                },
                enabled = areButtonsEnabled,
            ) {
                Text(text = stringResource(id = item.titleResId))
            }
        }
    }
}



