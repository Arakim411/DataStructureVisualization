package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ErrorState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.IdleState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.InitializingState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Composable
internal fun StateView(
    state: ChooseDataStructureState,
    onAddDataStructure: () -> Unit,
    onDeleteDataStructure: (id: Int) -> Unit,
) {

    Crossfade(
        targetState = state,
        label = "",
    ) { stateValue ->
        when (stateValue) {
            ErrorState -> ErrorStateView()
            IdleState -> Unit
            InitializingState -> LoadingView()
            is ReadyState ->  StateReadyView(
                dataStructures = stateValue.dataStructures,
                onAddDataStructure = onAddDataStructure,
                onDeleteDataStructure = onDeleteDataStructure,
            )
        }
    }
}

@Composable
private fun ErrorStateView() {
    //TODO implement, add common error view
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = "Something went wrong")
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Companion.Center,
    ) {
        CircularProgressIndicator()
    }
}
