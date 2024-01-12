package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonTopAppBar
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.BinarySearchTreeDestination
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination.HashMapDestination
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.ChooseDataStructureViewModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.chooseStructureTypeView.CreateDataStructureDialog
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.CreateDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.DeleteDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.UpdateDataStructureIsFavoriteAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureSideEffect.FailedToCreateDataStructures
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureSideEffect.FailedToGetDataStructures
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.BinarySearchTree
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.HashMap
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.LinkedList
import com.arakim.datastructurevisualization.ui.common.uimodel.allDataStructuresTypeUiModels
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R.drawable
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R.string
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun ChooseDataStructureScreen(
    navUiControllerState: NavigationUiControllerState,
    navigate: (MainDestination) -> Unit,
) {

    val viewModel = hiltViewModel<ChooseDataStructureViewModel>()
    val state = viewModel.presenter.stateFlow.collectAsStateWithLifecycle().value

    val isCreatingDataStructure = rememberSaveable { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    suspend fun showSnackBar(text: String) {
        snackBarHostState.showSnackbar(text)
    }

    val failedToGetMessage = stringResource(id = string.failed_to_get_data_structures_message)
    val failedToCreateMessage = stringResource(id = string.failed_to_create_data_structures_message)
    LaunchedEffect(Unit) {

        viewModel.presenter.onAction(InitializeAction)
        viewModel.presenter.sideEffectFlow.onEach {
            when (it) {
                FailedToGetDataStructures -> showSnackBar(failedToGetMessage)
                FailedToCreateDataStructures -> showSnackBar(failedToCreateMessage)
            }
        }.launchIn(this)
    }



    @Stable
    fun onAction(action: ChooseDataStructureAction) {
        viewModel.presenter.onAction(action)
    }

    if (isCreatingDataStructure.value) {
        CreateDataStructureDialog(
            availableTypes = allDataStructuresTypeUiModels,
            onCreate = { name, type ->
                onAction(CreateDataStructureAction(name, type))
            },
            onDismissRequest = {
                isCreatingDataStructure.value = false
            },
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
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
                onDataStructureClick = { dataStructure ->
                    when (dataStructure.dataStructureType) {
                        BinarySearchTree -> navigate(BinarySearchTreeDestination(dataStructure.id))
                        HashMap -> navigate(HashMapDestination(dataStructure.id))
                        LinkedList -> TODO()
                    }
                },
                onAddDataStructure = {
                    isCreatingDataStructure.value = true
                },
                onDeleteDataStructure = { dataStructureId ->
                    onAction(DeleteDataStructureAction(dataStructureId))
                },
                onUpdateIsFavorite = { dataStructureId, isFavorite ->
                    onAction(UpdateDataStructureIsFavoriteAction(dataStructureId, isFavorite))
                },
            )
        }
    }
}