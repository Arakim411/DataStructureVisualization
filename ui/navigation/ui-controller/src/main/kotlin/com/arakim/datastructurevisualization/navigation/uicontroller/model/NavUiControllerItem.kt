package com.arakim.datastructurevisualization.navigation.uicontroller.model

import androidx.annotation.DrawableRes

data class NavUiControllerItem(
    val title: String,
    val route: String,
    @DrawableRes val iconId: Int,
)