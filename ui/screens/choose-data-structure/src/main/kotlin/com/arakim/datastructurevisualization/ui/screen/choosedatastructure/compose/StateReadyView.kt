package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arakim.datastructurevisualization.ui.common.SwappableToDeleteBox
import com.arakim.datastructurevisualization.ui.common.list.CommonListItem
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Composable
internal fun StateReadyView(
    dataStructures: ImmutableList<DataStructureUiModel>,
    onDataStructureClick: (dataStructure: DataStructureUiModel) -> Unit,
    onAddDataStructure: () -> Unit,
    onDeleteDataStructure: (id: Int) -> Unit,
    onUpdateIsFavorite: (id: Int, isFavorite: Boolean) -> Unit,
) {

    if (dataStructures.isEmpty()) {
        DataStructuresEmptyView(onAddDataStructure)
    } else {
        DataStructuresListView(
            dataStructures = dataStructures,
            onDataStructureClick = onDataStructureClick,
            onDeleteDataStructure = onDeleteDataStructure,
            onUpdateIsFavorite = onUpdateIsFavorite,
        )
    }
}


@Composable
private fun DataStructuresEmptyView(
    onAddDataStructure: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.no_created_data_structure_message),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onAddDataStructure()
                },
            ) {
                Text(text = stringResource(id = R.string.create_data_structure_button))
            }

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DataStructuresListView(
    dataStructures: ImmutableList<DataStructureUiModel>,
    onDataStructureClick: (dataStructure: DataStructureUiModel) -> Unit,
    onDeleteDataStructure: (id: Int) -> Unit,
    onUpdateIsFavorite: (id: Int, isFavorite: Boolean) -> Unit,
) {

    LazyColumn {
        items(dataStructures.size) { index ->

            val dataStructure = dataStructures[index]
            SwappableToDeleteBox(
                onDelete = {
                    onDeleteDataStructure(dataStructure.id)
                },
            ) {
                DataStructureListItem(
                    modifier = Modifier
                        .animateItemPlacement()
                        .clickable {
                            onDataStructureClick(dataStructure)
                        },
                    item = dataStructure,
                    onUpdateIsFavorite = onUpdateIsFavorite,
                )
            }
            Divider()

        }
    }
}

@Composable
private fun DataStructureListItem(
    modifier: Modifier,
    item: DataStructureUiModel,
    onUpdateIsFavorite: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val trailingIcon = if (item.isFavorite) {
        R.drawable.baseline_favorite_24
    } else {
        R.drawable.baseline_not_favorite_24
    }

    CommonListItem(
        modifier = modifier,
        headLine = item.customName,
        leadingIcon = item.dataStructureType.iconResId,
        supportingText = item.dataStructureType.name,
        trailingIcon = trailingIcon,
        onTrailingIconClick = {
            onUpdateIsFavorite(item.id, !item.isFavorite)
        }
    )
}