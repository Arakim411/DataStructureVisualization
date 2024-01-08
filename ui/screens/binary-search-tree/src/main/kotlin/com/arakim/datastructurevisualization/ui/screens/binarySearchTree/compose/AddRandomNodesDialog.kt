package com.arakim.datastructurevisualization.ui.screens.binarySearchTree.compose

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
import com.arakim.datastructurevisualization.ui.common.dialogs.CommonDialogContainer
import com.arakim.datastructurevisualization.ui.common.inputWithActionsBottomSheet.NumericInputTextField
import com.arakim.datastructurevisualization.ui.screens.binarySearchTree.presenter.BinarySearchTreeAction.UpdateTreeAction.AddRandomNodesAction
import com.arakim.datastructurevisualization.ui.screens.binarysearchtree.R

@Composable
fun AddRandomNodesDialog(
    onDismissRequest: () -> Unit,
    onAction: (AddRandomNodesAction) -> Unit
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
                    label = { Text(stringResource(id = R.string.add_random_nodes_input_hint)) },
                )

                Button(
                    enabled = currentValue != null,
                    onClick = {
                        onDismissRequest()
                        onAction(AddRandomNodesAction(currentValue!!.toInt()))
                    },
                ) {
                    Text(text = stringResource(id = R.string.add_random_nodes_confirm_btn_text))
                }
            }
        }
    }
}