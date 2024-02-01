package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogBottomTextButtons
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogDimens
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.creationStage.CreationStage.ChooseNameState
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.ChooseDataStructureTestTags
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R

@Composable
internal fun ColumnScope.ChooseStructureNameView(
    stage: ChooseNameState,
    onNameChosen: (DataStructureTypeUiModel, name: String) -> Unit,
    onBack: () -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.testTag(ChooseDataStructureTestTags.InputDataStructureName),
        value = name,
        onValueChange = {
            name = it
        },
    )

    Spacer(modifier = Modifier.height(DialogDimens.BodyActionsPadding))

    DialogBottomTextButtons(
        onCancel = onBack,
        onAccept = {
            onNameChosen(stage.type,name)
        },
        isAcceptEnabled = name.isNotEmpty(),
        overrideCancelText = stringResource(id = R.string.back)
    )
}