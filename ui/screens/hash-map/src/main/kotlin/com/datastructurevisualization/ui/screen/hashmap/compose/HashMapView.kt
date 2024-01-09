package com.datastructurevisualization.ui.screen.hashmap.compose

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonErrorView
import com.arakim.datastructurevisualization.ui.common.CommonLoaderView
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapPresenter
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ErrorState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.IdleState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.InitializingState
import com.datastructurevisualization.ui.screen.hashmap.presenter.HashMapState.ReadyState

@Composable
fun HashMapView(
    presenter: HashMapPresenter,
    navigationUiControllerState: NavigationUiControllerState,
) {
    val state = presenter.stateFlow.collectAsState()

    Crossfade(targetState = state.value, label = "") { stateValue ->
        when (stateValue) {
            ErrorState -> CommonErrorView()
            IdleState, InitializingState -> CommonLoaderView()
            is ReadyState -> ReadyState(
                state = stateValue,
                navigationUiControllerState = navigationUiControllerState,
            )
        }
    }
}

@Composable
private fun ReadyState(
    state: ReadyState,
    navigationUiControllerState: NavigationUiControllerState,
) {
    Text(text = "ready state")

}