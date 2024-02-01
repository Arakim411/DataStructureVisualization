package com.arakim.datastructurevisualization.screens.choosedatastructure.compose.robot

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.arakim.datastructurevisualization.navigation.uicontroller.NavigationUiControllerState
import com.arakim.datastructurevisualization.navigation.uicontroller.UiControllerType
import com.arakim.datastructurevisualization.ui.common.CommonTestTags
import com.arakim.datastructurevisualization.ui.navigation.destination.MainDestination
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.ChooseDataStructureViewModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.ChooseDataStructureScreen
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.ChooseDataStructureTestTags
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructurePresenter
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.IdleState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.SideEffect
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.State
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.LocalWindowSize
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSize
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Compact
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow

typealias Tags = ChooseDataStructureTestTags

class ChooseDataStructureScreenRobot(
    private val composeRule: ComposeContentTestRule,
    private val context: Context,
) {

    @MockK
    lateinit var presenter: ChooseDataStructurePresenter

    @MockK
    lateinit var navigate: (MainDestination) -> Unit

    val createDataStructureRobot = CreateDataStructureRobot(composeRule, context)

    val viewModel: ChooseDataStructureViewModel by lazy { ChooseDataStructureViewModel(presenter) }
    val fakeSideEffectFlow = MutableSharedFlow<SideEffect>()
    val fakeStateFlow = MutableStateFlow<State>(IdleState)
    var fakeNavUiControllerState = NavigationUiControllerState(UiControllerType.Drawer)

    init {
        MockKAnnotations.init(this)
        every { presenter.initialize(any()) } just runs
        every { presenter.sideEffectFlow } returns fakeSideEffectFlow
        every { presenter.stateFlow } returns fakeStateFlow
        every { presenter.onAction(any()) } just runs
        every { navigate(any()) } just runs
    }

    fun goToReadyEmptyState() {
        fakeStateFlow.value = ReadyState(immutableListOf())
    }

    @Composable
    fun ChooseDataStructureScreen() {
        // TODO handle
        CompositionLocalProvider(LocalWindowSize provides WindowSize(Compact, Compact)) {
            ChooseDataStructureScreen(
                viewModel = viewModel,
                navUiControllerState = fakeNavUiControllerState,
                navigate = navigate,
            )
        }
    }
}