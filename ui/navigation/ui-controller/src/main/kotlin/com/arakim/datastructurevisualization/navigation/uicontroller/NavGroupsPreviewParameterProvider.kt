package com.arakim.datastructurevisualization.navigation.uicontroller

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerGroup
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerItem
import com.arakim.datastructurevisualization.ui.navigation.uicontroller.R
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf

internal class NavGroupsPreviewParameterProvider :
    PreviewParameterProvider<ImmutableList<NavUiControllerGroup>> {
    override val values: Sequence<ImmutableList<NavUiControllerGroup>>
        get() = sequenceOf(
            listWithOneGroup(),
            listWith2groups(),
        )

    private fun listWithOneGroup() = immutableListOf(
        NavUiControllerGroup(
            name = "data structures",
            items = immutableListOf(
                NavUiControllerItem(
                    title = "title",
                    route = "route1",
                    iconId = R.drawable.ic_for_preview,
                ),
                NavUiControllerItem(
                    title = "title",
                    route = "route2",
                    iconId = R.drawable.ic_for_preview,
                )
            )
        ),
    )

    private fun listWith2groups() = immutableListOf(
        NavUiControllerGroup(
            name = "data structures",
            items = immutableListOf(
                NavUiControllerItem(
                    title = "title",
                    route = "route1",
                    iconId = R.drawable.ic_for_preview,
                ),
                NavUiControllerItem(
                    title = "title",
                    route = "route2",
                    iconId = R.drawable.ic_for_preview,
                ),
                NavUiControllerItem(
                    title = "title",
                    route = "route3",
                    iconId = R.drawable.ic_for_preview,
                )
            )
        ),
        NavUiControllerGroup(
            name = "other",
            items = immutableListOf(
                NavUiControllerItem(
                    title = "title",
                    route = "route4",
                    iconId = R.drawable.ic_for_preview,
                ),
                NavUiControllerItem(
                    title = "title",
                    route = "route5",
                    iconId = R.drawable.ic_for_preview,
                ),
            )
        )
    )


}