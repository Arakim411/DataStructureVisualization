package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter

import androidx.compose.runtime.Immutable
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Immutable
sealed interface DeletedDataStructuresState {

    object IdleState : DeletedDataStructuresState
    object InitializingState : DeletedDataStructuresState
    object ErrorState : DeletedDataStructuresState

    @Immutable
    data class ReadyState(
        val dataStructures: ImmutableList<DataStructureUiModel>
    ) : DeletedDataStructuresState
}