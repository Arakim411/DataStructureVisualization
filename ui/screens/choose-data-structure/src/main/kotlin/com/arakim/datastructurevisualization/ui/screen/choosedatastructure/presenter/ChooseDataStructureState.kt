package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter

import androidx.compose.runtime.Immutable
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Immutable
sealed interface ChooseDataStructureState {

    @Immutable
    object IdleState : ChooseDataStructureState

    object InitializingState : ChooseDataStructureState

    object ErrorState: ChooseDataStructureState

    @Immutable
    data class ReadyState(
        val dataStructures: ImmutableList<DataStructureUiModel>
    ): ChooseDataStructureState

}