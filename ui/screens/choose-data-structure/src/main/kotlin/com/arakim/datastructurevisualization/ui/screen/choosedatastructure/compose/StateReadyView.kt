package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Composable
internal fun StateReadyView(dataStructures: ImmutableList<DataStructureUiModel>) {

    if (dataStructures.isEmpty()) {
        DataStructuresEmptyView()
    } else {
        DataStructuresListView(dataStructures = dataStructures)
    }
}


@Composable
private fun DataStructuresEmptyView() {
    Text(text = "empty")
}

@Composable
private fun DataStructuresListView(dataStructures: ImmutableList<DataStructureUiModel>) {

    Column {
        dataStructures.forEach { dataStructure ->
            Text(text = dataStructure.customName)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}