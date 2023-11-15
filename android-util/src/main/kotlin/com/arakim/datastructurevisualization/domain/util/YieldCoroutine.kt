package com.arakim.datastructurevisualization.domain.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

inline fun CoroutineScope.yielded(
    crossinline action: suspend () -> Unit
) {
    launch {
        yield()
        action()
    }
}
