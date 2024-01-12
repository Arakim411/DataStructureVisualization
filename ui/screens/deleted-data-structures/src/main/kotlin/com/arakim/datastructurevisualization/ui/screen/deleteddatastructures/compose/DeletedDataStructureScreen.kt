package com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonErrorView
import com.arakim.datastructurevisualization.ui.common.CommonLoaderView
import com.arakim.datastructurevisualization.ui.common.CommonTopAppBar
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.DeletedDataStructureViewModel
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructureAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.ErrorState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.IdleState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.InitializingState
import com.arakim.datastructurevisualization.ui.screen.deleteddatastructures.presenter.DeletedDataStructuresState.ReadyState
import com.arakim.datastructurevisualization.ui.screens.deleteddatastructures.R

@Composable
fun DeletedDataStructureScreen(
    navUiControllerState: NavigationUiControllerState,
) {

    val viewModel = hiltViewModel<DeletedDataStructureViewModel>()
    val state = viewModel.presenter.stateFlow.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.presenter.onAction(InitializeAction)
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = stringResource(id = R.string.deleted_data_structures_screen_title),
                navigationUiControllerState = navUiControllerState,
            )

        },
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Crossfade(targetState = state, label = "") { stateValue ->
                when (stateValue) {
                    ErrorState -> CommonErrorView()
                    IdleState -> Unit
                    InitializingState -> CommonLoaderView()
                    is ReadyState -> ReadyStateView(stateValue)
                }
            }
        }
    }

}

@Composable
private fun ReadyStateView(
    state: ReadyState,
) {

    if (state.dataStructures.isNotEmpty()) {
        Column {
            state.dataStructures.forEach {
                Text(text = it.customName)
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "empty")
        }
    }
}

