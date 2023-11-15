package com.arakim.datastructurevisualization.domain.util

import com.arakim.datastructurevisualization.kotlinutil.CommonError
import com.arakim.datastructurevisualization.kotlinutil.TypedResult
import kotlin.coroutines.cancellation.CancellationException

// TODO add sentry logger
suspend inline fun <T> executeCommonIoAction(
    noinline action: suspend () -> T,
): TypedResult<T, CommonError> = try {
    TypedResult.success(action())
} catch (e: CancellationException) {
    throw e
} catch (t: Throwable) {
    TypedResult.failure(CommonError)
}

