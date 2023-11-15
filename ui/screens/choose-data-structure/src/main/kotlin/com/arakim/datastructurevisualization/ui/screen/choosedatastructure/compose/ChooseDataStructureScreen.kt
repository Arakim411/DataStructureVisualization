package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonTopAppBar
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.ChooseDataStructureViewModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.CreateDataStructureDialog
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.CreateDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.model.DataStructureTypeUiModel
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R.drawable
import com.arakim.datastructurevisualization.ui.util.immutableListOf

@Composable
fun ChooseDataStructureScreen(
    navUiControllerState: NavigationUiControllerState
) {

    val viewModel = hiltViewModel<ChooseDataStructureViewModel>()
    val state = viewModel.presenter.stateFlow.collectAsStateWithLifecycle().value

    val isCreatingDataStructure = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.presenter.onAction(InitializeAction)
    }

    @Stable
    fun onAction(action: ChooseDataStructureAction) {
        viewModel.presenter.onAction(action)
    }

    if (isCreatingDataStructure.value) {
        CreateDataStructureDialog(
            availableTypes = remember { immutableListOf(DataStructureTypeUiModel.values().toList()) },
            onCreate = { name, type ->
                onAction(CreateDataStructureAction(name, type))
            },
            onDismissRequest = {
                isCreatingDataStructure.value = false
            },
        )
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = stringResource(id = R.string.choose_data_structure_screen_title),
                navigationUiControllerState = navUiControllerState,
            )

        },
        floatingActionButton = {
            if (state is ReadyState)
                FloatingActionButton(
                    onClick = {
                        isCreatingDataStructure.value = true
                    },
                ) {
                    Icon(
                        painter = painterResource(
                            id = drawable.baseline_add_24,
                        ),
                        contentDescription = null
                    )
                }
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            StateView(
                state = state,
                onAction = ::onAction,
            )
        }
    }

}