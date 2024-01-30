package com.arakim.datastructurevisualization.screens.choosedatastructure.compose

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.arakim.datastructurevisualization.screens.choosedatastructure.compose.robot.StateViewRobot
import com.arakim.datastructurevisualization.testdatagenerator.fakeDataStructures
import com.arakim.datastructurevisualization.ui.common.uimodel.toUiModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ErrorState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.InitializingState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class StateViewTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var robot: StateViewRobot

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context
        MockKAnnotations.init(this)

        robot = StateViewRobot(composeRule, context)
        robot.setUp()
    }

    @Test
    fun when_state_is_ErrorState_then_show_CommonErrorView() {
        composeRule.setContent {
            robot.StateView(state = ErrorState)
        }
        robot.assertErrorIsDisplayed()
    }

    @Test
    fun when_state_is_InitializingState_then_show_loader() {
        composeRule.setContent {
            robot.StateView(InitializingState)
        }
        robot.assertLoaderIsDisplayed()
    }

    //////////// ReadyState

    @Test
    fun when_state_is_ready_and_dataStructures_is_empty_then_show_empty_view() {
        val readyState = ReadyState(dataStructures = immutableListOf())

        composeRule.setContent {
            robot.StateView(readyState)
        }
        robot.assertEmptyIsDisplayed()
    }

    @Test
    fun when_state_is_ready_and_dataStructures_is_not_empty_then_show_list_view() {
        val dataStructure = fakeDataStructures(30).toUiModel()
        val readyState = ReadyState(dataStructure)

        composeRule.setContent {
            robot.StateView(readyState)
        }
        robot.apply {
            assertDataStructureListIsDisplayed(dataStructure)
            assertItemActionsInvokes(index = 0, item = dataStructure.first())
        }
    }
}
