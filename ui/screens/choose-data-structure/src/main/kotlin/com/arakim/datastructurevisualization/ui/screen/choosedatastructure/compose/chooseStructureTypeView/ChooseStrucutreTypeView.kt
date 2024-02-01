package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogBottomTextButtons
import com.arakim.datastructurevisualization.ui.common.dialogs.DialogDivider
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.ChooseDataStructureTestTags
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Composable
internal fun ColumnScope.ChooseStructureTypeView(
    availableTypes: ImmutableList<DataStructureTypeUiModel>,
    onTypeChosen: (DataStructureTypeUiModel) -> Unit,
    onCancel: () -> Unit,
) {

    var chosenType by remember { mutableStateOf<DataStructureTypeUiModel?>(null) }

    availableTypes.forEach { type ->
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                modifier = Modifier.testTag(ChooseDataStructureTestTags.ChooseStructureTypeRadioButton),
                selected = chosenType == type,
                onClick = { chosenType = type },
            )

            Text(
                text = type.name,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }

    DialogDivider()

    DialogBottomTextButtons(
        onCancel = onCancel,
        onAccept = {
            onTypeChosen(chosenType!!)
        },
        isAcceptEnabled = chosenType != null,
    )

}