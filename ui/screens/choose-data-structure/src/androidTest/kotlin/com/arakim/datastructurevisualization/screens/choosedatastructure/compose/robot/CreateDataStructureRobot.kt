package com.arakim.datastructurevisualization.screens.choosedatastructure.compose.robot

import android.content.Context
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.arakim.datastructurevisualization.ui.common.CommonTestTags
import com.arakim.datastructurevisualization.ui.common.R
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.ChooseDataStructureTestTags

class CreateDataStructureRobot(
    private val composeRule: ComposeContentTestRule,
    private val context: Context,
) {

    fun clickFloatingActionButton() {
        composeRule.onNodeWithTag(CommonTestTags.FloatingActionButton).performClick()
    }

    fun chooseFirstDataStructure() {
        val tag = Tags.ChooseStructureTypeRadioButton
        composeRule.onAllNodesWithTag(tag).onFirst().performClick()
    }

    fun clickAccept() {
        val accept = context.getString(R.string.dialog_accept_title)
        composeRule.onNodeWithText(accept).performClick()
    }

    fun enterName(name: String) {
        composeRule.onNodeWithTag(ChooseDataStructureTestTags.InputDataStructureName).performTextInput(name)
    }
}