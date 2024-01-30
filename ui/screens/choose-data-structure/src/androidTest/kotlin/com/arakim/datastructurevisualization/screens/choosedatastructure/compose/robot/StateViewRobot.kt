package com.arakim.datastructurevisualization.screens.choosedatastructure.compose.robot

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import com.arakim.datastructurevisualization.ui.common.CommonTestTags
import com.arakim.datastructurevisualization.ui.common.uimodel.DataStructureUiModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState
import com.arakim.datastructurevisualization.ui.screens.choosedatastructure.R
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify

class StateViewRobot(
    private val composeRule: ComposeContentTestRule,
    private val context: Context,
) {
    private val onAddClick: () -> Unit = mockk()
    private val onDataStructureClick: (dataStructure: DataStructureUiModel) -> Unit = mockk()
    private val onDeleteClick: (id: Int) -> Unit = mockk()
    private val onUpdateFavoriteClick: (Int, Boolean) -> Unit = mockk()

    fun setUp() {
        every { onAddClick() } just runs
        every { onDataStructureClick(any()) } just runs
        every { onDeleteClick(any()) } just runs
        every { onUpdateFavoriteClick(any(), any()) } just runs
    }

    fun assertErrorIsDisplayed() = composeRule.onNode(hasTestTag(CommonTestTags.ErrorView)).assertExists()
    fun assertLoaderIsDisplayed() = composeRule.onNode(hasTestTag(CommonTestTags.LoaderView)).assertExists()
    fun assertEmptyIsDisplayed() {
        val emptyText = context.resources.getString(R.string.no_created_data_structure_message)
        composeRule.onNode(hasText(emptyText)).assertExists()
    }

    fun assertDataStructureListIsDisplayed(items: ImmutableList<DataStructureUiModel>) {
        val listView = composeRule.onNode(hasScrollAction())
        listView.assertIsDisplayed()
        items.forEachIndexed { index, item ->
            listView.performScrollToIndex(index)
            composeRule.onNodeWithText(item.customName).assertIsDisplayed()
        }
    }

    fun assertItemActionsInvokes(index: Int, item: DataStructureUiModel) {
        composeRule.onNode(hasScrollAction()).performScrollToIndex(index)
        val itemView = composeRule.onNodeWithText(item.customName)
        val favoriteIconView = itemView.onChildren().filterToOne(hasTestTag(CommonTestTags.CommonListItemTrailingIcon))

        favoriteIconView.performClick()
        itemView.apply {
            assertIsDisplayed()
            performClick()
            performTouchInput {
                swipeLeft()
            }
        }

        verify {
            onDataStructureClick(item)
            onDeleteClick(item.id)
            onUpdateFavoriteClick(item.id, !item.isFavorite)
        }

    }


    @Composable
    fun StateView(
        state: ChooseDataStructureState,
    ) {
        com.arakim.datastructurevisualization.ui.screen.choosedatastructure.compose.StateView(
            state = state,
            onDataStructureClick = onDataStructureClick,
            onAddDataStructure = onAddClick,
            onDeleteDataStructure = onDeleteClick,
            onUpdateIsFavorite = onUpdateFavoriteClick,
        )
    }
}