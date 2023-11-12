package com.arakim.datastructurevisualization.ui.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.arakim.datastructurevisualization.ui.util.StringWrapper.StringResources
import com.arakim.datastructurevisualization.ui.util.StringWrapper.StringValue

sealed interface StringWrapper {

    data class StringResources(
        @StringRes val resId: Int
    ) : StringWrapper

    data class StringValue(
        val value: String
    ) : StringWrapper
}

@Composable
@ReadOnlyComposable
inline fun StringWrapper.getString() = when (this) {
    is StringResources -> stringResource(id = resId)
    is StringValue -> value
}