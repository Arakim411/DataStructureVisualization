package com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.ui.common.CommonTopAppBar
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.ChooseDataStructureViewModel
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R

@Composable
fun ChooseDataStructureScreen(
    navUiControllerState: NavigationUiControllerState
) {

    val viewModel = hiltViewModel<ChooseDataStructureViewModel>()

    CommonTopAppBar(
        title = stringResource(id = R.string.choose_data_structure_screen_title),
        navigationUiControllerState = navUiControllerState,
    )
    ChooseDataStructureView(chooseDataStructurePresenter = viewModel.presenter)


}