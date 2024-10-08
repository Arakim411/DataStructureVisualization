package com.arakim.datastructurevisualization.testutil

import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlin.reflect.KClass

abstract class StateTest {

    inline fun <reified T : Any> stateTest(
        vararg states: KClass<out T>,
        testBody: (T) -> Unit
    ) {
        states.forEach {
            when {
                it.objectInstance != null -> testBody(it.objectInstance!!)
                else -> mockkByClass(it)
            }
        }
    }

    inline fun <reified T : Any> runTestState(
        vararg states: KClass<out T>,
        crossinline testBody: suspend TestScope.(T) -> Unit
    ) = runTest {
        stateTest(
            states = states,
            testBody = { testBody(it) }
        )
    }

    inline fun <reified T : Any> runTestState(
        states: List<KClass<out T>>,
        crossinline testBody: suspend TestScope.(T) -> Unit
    ) = runTest {
        stateTest(
            states = states.toTypedArray(),
            testBody = { testBody(it) }
        )
    }
}
