package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model

import androidx.compose.runtime.Immutable
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.mapToImmutable
import com.arakim.datastrucutrevisualization.domain.dataStructures.model.DataStructure

@Immutable
data class DataStructureUiModel(
    val id: Int,
    val customName: String,
    val dataStructureType: DataStructureTypeUiModel,
)

internal fun List<DataStructure>.toUiModel(

): ImmutableList<DataStructureUiModel> = mapToImmutable { it.toUiModel() }

internal fun DataStructure.toUiModel(): DataStructureUiModel = DataStructureUiModel(
    id = id,
    customName = name,
    dataStructureType = type.toUiModel()
)

