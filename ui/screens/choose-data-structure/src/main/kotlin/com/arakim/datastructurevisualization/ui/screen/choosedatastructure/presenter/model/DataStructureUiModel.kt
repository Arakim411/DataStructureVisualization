package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model

import androidx.compose.runtime.Immutable

@Immutable
data class DataStructureUiModel(
    val id: String,
    val customName: String,
    val dataStructureType: DataStructureTypeUiModel,
)