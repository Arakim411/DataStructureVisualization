package com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            var currentValue: Number? by rememberSaveable { mutableStateOf(null) }

            NumericInputTextField(
                onValueChange = {
                    currentValue = it
                },
                label = label?.let { { Text(text = it) } },
            )

            ActionButtons(
                actions = actions,
                getCurrentNumber = { currentValue!! },
                onDismissRequest = onDismissRequest,
                areButtonsEnabled = currentValue != null,
            )

            // TODO handle like grid for more 4+ actions

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



