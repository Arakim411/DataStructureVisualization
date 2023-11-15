package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.arakim.datastructurevisualization.ui.common.dialogs.CommonDialogContainer
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogDivider
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogHeadline
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.CreationStage.ChooseNameState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.CreationStage.ChooseTypeStage
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R
import com.arakim.datastructurevisualization.ui.util.ImmutableList

//TODO extract to common all dialogs to stick m3 rules
@Composable
fun CreateDataStructureDialog(
    availableTypes: ImmutableList<DataStructureTypeUiModel>,
    onCreate: (name: String, DataStructureTypeUiModel) -> Unit,
    onDismissRequest: () -> Unit
) {
    val stage = remember { mutableStateOf<CreationStage>(ChooseTypeStage) }

    Dialog(onDismissRequest = onDismissRequest) {
        CommonDialogContainer {
            Column {
                DialogHeadline(text = stage.value.getTitle())

                DialogDivider()

                StageView(
                    stage = stage,
                    onCreate = onCreate,
                    availableTypes = availableTypes,
                    onDismissRequest = onDismissRequest,
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.StageView(
    stage: MutableState<CreationStage>,
    onCreate: (name: String, DataStructureTypeUiModel) -> Unit,
    availableTypes: ImmutableList<DataStructureTypeUiModel>,
    onDismissRequest: () -> Unit,
) {
    Column {
        when (val stageValue = stage.value) {
            ChooseTypeStage -> ChooseStructureTypeView(
                availableTypes = availableTypes,
                onTypeChosen = { type ->
                    stage.value = ChooseNameState(type)
                },
                onCancel = onDismissRequest,
            )

            is ChooseNameState -> ChooseStructureNameView(
                stage = stageValue,
                onNameChosen = { type, name ->
                    onCreate(name, type)
                    onDismissRequest()
                },
                onBack = {
                    stage.value = ChooseTypeStage
                },
            )
        }
    }
}

internal sealed interface CreationStage {

    object ChooseTypeStage : CreationStage
    data class ChooseNameState(val type: DataStructureTypeUiModel) : CreationStage
}

@Composable
private fun CreationStage.getTitle(): String = when (this) {
    is ChooseNameState -> stringResource(R.string.create_data_structure_choose_name_title)
    ChooseTypeStage -> stringResource(R.string.create_data_structure_choose_type_title)
}