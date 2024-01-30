package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose

import androidx.compose.runtime.Composable
import com.arakim.datastructurevisualization.ui.common.CommonErrorView
import com.arakim.datastructurevisualization.ui.common.CommonLoaderView
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ErrorState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.IdleState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.InitializingState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureUiModel

@Composable
internal fun StateView(
    state: ChooseDataStructureState,
    onDataStructureClick: (dataStructure: DataStructureUiModel) -> Unit,
    onAddDataStructure: () -> Unit,
    onDeleteDataStructure: (id: Int) -> Unit,
    onUpdateIsFavorite: (id: Int, isFavorite: Boolean) -> Unit,
) {
        when (state) {
            ErrorState -> CommonErrorView()
            IdleState -> Unit
            InitializingState -> CommonLoaderView()
            is ReadyState ->  StateReadyView(
                dataStructures = state.dataStructures,
                onDataStructureClick = onDataStructureClick,
                onAddDataStructure = onAddDataStructure,
                onDeleteDataStructure = onDeleteDataStructure,
                onUpdateIsFavorite = onUpdateIsFavorite,
            )
        }

}
