package com.arakim.datastructurevisualization.ui.screens.choosedatastructure.presenter

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.arakim.datastructurevisualization.ui.mvi.StateReducerWithSideEffect
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructurePresenter
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.IdleState
import io.mockk.MockKAnnotations
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineScope
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

private typealias MockHelper = ChooseDataStructurePresenterMockHelper

class ChooseDataStructurePresenterTest {

    private val mockHelper: MockHelper = MockHelper()
    private lateinit var subject: ChooseDataStructurePresenter

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        mockHelper.apply {
            subject = ChooseDataStructurePresenter(
                initializeReducer,
                createdDataStructureReducer,
                deleteDataStructureReducer,
                updateIsFavoriteReducer,
            )
        }
    }

    @Test
    fun `when created initial state should be Idle`() {
        assertThat(subject.stateFlow.value).isEqualTo(IdleState)
    }

    @Test
    fun `when initialized all reducers should be also initialized`() = with(mockHelper) {
        val coroutineScope = mockk<CoroutineScope>()
        mockReducersInitialization(coroutineScope)

        subject.initialize(coroutineScope)

        verify(exactly = 1) {
            reducers.forEach { reducer ->
                when (reducer) {
                    is StateReducerWithSideEffect<*, *, *, *> -> reducer.initialize(
                        coroutineScope = coroutineScope,
                        onAction = any(),
                        logInvalidState = any(),
                        emitSideEffect = any(),
                    )

                    else -> reducer.initialize(
                        coroutineScope = coroutineScope,
                        onAction = any(),
                        logInvalidState = any(),
                    )
                }
            }
        }
    }
}