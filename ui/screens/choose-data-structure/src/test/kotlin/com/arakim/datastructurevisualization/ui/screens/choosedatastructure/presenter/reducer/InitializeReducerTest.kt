@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.arakim.datastructurevisualization.ui.screens.choosedatastructure.presenter.reducer

import assertk.assertThat
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import com.arakim.datastructurevisualization.testdatagenerator.fakeDataStructures
import com.arakim.datastructurevisualization.testutil.CoroutineTest
import com.arakim.datastructurevisualization.testutil.allDerivedSealedClassesRecursive
import com.arakim.datastructurevisualization.ui.common.uimodel.toUiModel
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.Action
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializeAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializedAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureAction.InitializationAction.InitializedFailedAction
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureSideEffect.FailedToGetDataStructures
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ErrorState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.IdleState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.InitializingState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.ChooseDataStructureState.ReadyState
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.SideEffect
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.State
import com.arakim.datastructurevisualization.ui.screen.choosedatastructure.presenter.reducer.InitializeReducer
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.DsResult
import com.arakim.datastrucutrevisualization.domain.dataStructures.useCases.ListenForDataStructuresUpdateUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InitializeReducerTest : CoroutineTest() {

    private lateinit var sujbect: InitializeReducer

    private lateinit var fakeUpdatesFlow: MutableSharedFlow<DsResult>

    @MockK
    private lateinit var mockUpdatesUseCase: ListenForDataStructuresUpdateUseCase

    @MockK
    private lateinit var onnAction: (Action) -> Unit

    @MockK
    private lateinit var logInvalidState: (State) -> State

    @MockK
    private lateinit var emitSideEffect: (SideEffect) -> Unit

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        every { onnAction.invoke(any()) } returns Unit
        every { logInvalidState.invoke(any()) } answers { firstArg() }
        every { emitSideEffect(any()) } just runs

        fakeUpdatesFlow = MutableSharedFlow()
        every { mockUpdatesUseCase.invoke() } returns fakeUpdatesFlow
        sujbect = InitializeReducer(mockUpdatesUseCase)
        sujbect.initialize(coroutineScope, onnAction, logInvalidState, emitSideEffect)
    }

    // Initialize Action

    //TODO name to indicate initialization is success
    @Test
    fun `when InitializeAction is received in IdleState then initialize`() = runTestState<State>(
        IdleState::class,
    ) { state ->
        val fakeDataStructures = fakeDataStructures()

        val resultState = with(sujbect) { state.reduce(InitializeAction) }
        advanceUntilIdle()
        fakeUpdatesFlow.emit(TypedResult.Success(fakeDataStructures))
        advanceUntilIdle()

        assertThat(resultState).isInstanceOf<InitializingState>()
        verify {
            mockUpdatesUseCase.invoke()
            onnAction.invoke(InitializedAction(fakeDataStructures.toUiModel()))
        }
    }

    @Test
    fun `when InitializeAction is received in IdleState and initialize failed then emit initialize failed action`() =
        runTestState<State>(
            IdleState::class,
        ) { state ->
            val resultState = with(sujbect) { state.reduce(InitializeAction) }
            advanceUntilIdle()
            fakeUpdatesFlow.emit(TypedResult.Failure(CommonError))
            advanceUntilIdle()

            assertThat(resultState).isInstanceOf<InitializingState>()
            verify {
                mockUpdatesUseCase.invoke()
                onnAction(InitializedFailedAction)
                emitSideEffect(FailedToGetDataStructures)
            }
        }

    @Test
    fun `when InitializeAction is received in state Other then IdleState, then invoke logInvalidState()`() =
        runTestState<State>(
            ChooseDataStructureState::class.allDerivedSealedClassesRecursive()
                .filter { !it.isInstance(IdleState) },
        ) { state ->
            val resultState = with(sujbect) { state.reduce(InitializeAction) }

            assertThat(resultState === state).isTrue()
            verify { logInvalidState.invoke(state) }
        }

    // Initialized Action

    @Test
    fun `when InitializedAction is received in InitializingState then goes to ReadyState`() = runTestState(
        InitializingState::class,
    ) { state ->
        val fakeDataStructures = fakeDataStructures().toUiModel()
        val resultState = with(sujbect) { state.reduce(InitializedAction(fakeDataStructures)) }

        assertThat(resultState).isInstanceOf<ReadyState>()
    }

    @Test
    fun `when InitializedAction is received in ReadyState then update data structures`() {
        val readyState = ReadyState(dataStructures = mockk())
        val newDataStructures = fakeDataStructures().toUiModel()
        val initializedAction = InitializedAction(newDataStructures)

        val resultState = with(sujbect) { readyState.reduce(initializedAction) }

        assertThat(resultState is ReadyState && resultState.dataStructures === newDataStructures).isTrue()
    }

    @Test
    fun `when InitializedAction is received in state other than IdleState, ReadyState, then logInvalidState()`() =
        runTestState(
            ErrorState::class,
            IdleState::class,
        ) { state ->
            with(sujbect) { state.reduce(InitializedAction(mockk())) }
            verify { logInvalidState.invoke(state) }
        }

    // InitializedFailed Action
    @Test
    fun `when InitializedFailedAction is received in InitializingState then goes to ErrorState`() =
        runTestState(
            InitializingState::class,
        ) { state ->
            val resultState = with(sujbect) { state.reduce(InitializedFailedAction) }
            assertThat(resultState).isInstanceOf<ErrorState>()
        }

    @Test
    fun `when InitializedFailedAction is received in state other than InitializingState then logInvalidState()`() =
        runTestState(
            ChooseDataStructureState::class.allDerivedSealedClassesRecursive()
                .filter { !it.isInstance(InitializingState) }
        ) { state ->
            with(sujbect) { state.reduce(InitializedFailedAction) }
            verify {
                logInvalidState(state)
            }
        }

}