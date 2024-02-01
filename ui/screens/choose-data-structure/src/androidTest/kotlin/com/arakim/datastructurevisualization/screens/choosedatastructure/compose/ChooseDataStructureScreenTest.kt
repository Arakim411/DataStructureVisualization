package com.arakim.datastructurevisualization.screens.choosedatastructure.compose

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.platform.app.InstrumentationRegistry
import com.arakim.datastructurevisualization.screens.choosedatastructure.compose.robot.ChooseDataStructureScreenRobot
import com.arakim.datastructurevisualization.testdatagenerator.randomString
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureTypeUiModel.BinarySearchTree
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.CreateDataStructureAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureSideEffect.FailedToCreateDataStructures
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureSideEffect.FailedToGetDataStructures
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R.string
import io.mockk.MockKAnnotations
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ChooseDataStructureScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var context: Context
    lateinit var robot: ChooseDataStructureScreenRobot

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        context = InstrumentationRegistry.getInstrumentation().context
        robot = ChooseDataStructureScreenRobot(composeRule, context)
    }

    @Test
    fun when_screen_initialize_then_send_InitializeAction() {
        composeRule.setContent {
            robot.ChooseDataStructureScreen()
        }
        verify {
            robot.presenter.onAction(InitializeAction)
        }
    }

    @Test
    fun when_FailedToGetDataStructures_sideEffect_is_emitted_than_handle_it() = runTest {
        val expectedText = context.getString(string.failed_to_get_data_structures_message)
        composeRule.setContent {
            robot.ChooseDataStructureScreen()
        }
        robot.fakeSideEffectFlow.emit(FailedToGetDataStructures)
        composeRule.onNodeWithText(expectedText).assertExists()
    }

    @Test
    fun when_FailedToCreateDataStructures_sideEffect_is_emitted_than_handle_it() = runTest {
        val expectedText = context.getString(string.failed_to_create_data_structures_message)
        composeRule.setContent {
            robot.ChooseDataStructureScreen()
        }
        robot.fakeSideEffectFlow.emit(FailedToCreateDataStructures)
        composeRule.onNodeWithText(expectedText).assertExists()
    }

    @Test
    fun when_floating_button_is_clicked_then_start_creating_data_structure_flow() {
        val randomName = randomString()
        composeRule.setContent {
            robot.apply {
                goToReadyEmptyState()
                ChooseDataStructureScreen()
            }
        }

        robot.createDataStructureRobot.apply {
            clickFloatingActionButton()
            chooseFirstDataStructure()
            clickAccept()
            enterName(randomName)
            clickAccept()
        }
        verify {
            robot.presenter.onAction(CreateDataStructureAction(randomName, BinarySearchTree))
        }
    }
}