package com.arakim.datastructurevisualization.navigation.uicontroller.model

import androidx.compose.runtime.Immutable
import com.arakim.datastructurevisualization.ui.util.ImmutableList

@Immutable
data class NavUiControllerGroup(
    val name: String,
    val items: ImmutableList<NavUiControllerItem>,
)