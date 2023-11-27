package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.arakim.datastructurevisualization.ui.common.dialogs.CommonDialogContainer
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogDivider
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogHeadline
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.ChooseNameState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.ChooseTypeStage
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.ImpactProperty.Width
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeDelimiter
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Compact
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Expanded
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Medium

//TODO extract to common all dialogs to stick m3 rules
@Composable
fun CreateDataStructureDialog(
    availableTypes: ImmutableList<DataStructureTypeUiModel>,
    onCreate: (name: String, DataStructureTypeUiModel) -> Unit,
    onDismissRequest: () -> Unit
) {
    val stage = rememberSaveable(stateSaver = CreationStage.Saver) { mutableStateOf(ChooseTypeStage) }

    Dialog(onDismissRequest = onDismissRequest) {
        WindowSizeDelimiter(impactedProperty = Width) {

            CommonDialogContainer {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally) {
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
}

@Composable
private fun StageView(
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

@Composable
private fun CreationStage.getTitle(): String = when (this) {
    is ChooseNameState -> stringResource(R.string.create_data_structure_choose_name_title)
    ChooseTypeStage -> stringResource(R.string.create_data_structure_choose_type_title)
}