package com.arakim.datastructurevisualization.navigation.uicontroller

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arakim.datastructurevisualization.navigation.uicontroller.modalNavigationDrawer.ModalNavigationDrawer
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerGroup
import com.arakim.datastructurevisualization.navigation.uicontroller.model.NavUiControllerItem
import com.arakim.datastructurevisualization.ui.navigation.uicontroller.R
import com.arakim.datastructurevisualization.ui.util.ImmutableList
import com.arakim.datastructurevisualization.ui.util.immutableListOf
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.FakeWindowSizeType
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.ImpactProperty
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeDelimiter
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType
import kotlinx.coroutines.launch

@Composable
fun NavigationUiController(
    navigationUiControllerState: NavigationUiControllerState,
    navUiControllerGroups: ImmutableList<NavUiControllerGroup>,
    onItemClick: (NavUiControllerItem) -> Unit,
    selectedRoute: String,
    content: @Composable () -> Unit,
) {
    WindowSizeDelimiter(impactedProperty = ImpactProperty.Width) { windowSizeType ->
        when (windowSizeType) {
            WindowSizeType.Compact, WindowSizeType.Medium -> ModalNavigationDrawer(
                navigationUiControllerState = navigationUiControllerState,
                navigationGroups = navUiControllerGroups,
                onItemClick = onItemClick,
                selectedRoute = selectedRoute,
                content = content,
            )

            WindowSizeType.Expanded -> TODO()
        }
    }
}

@Preview
@Composable
fun NavigationUiControllerPreview() {
    val fakeNavGroups = immutableListOf(
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
    val navUiControllerState = rememberNavUiControllerState()
    val selectedRoute = remember { mutableStateOf(fakeNavGroups[0].items[0].route) }

    FakeWindowSizeType {
        NavigationUiController(
            navigationUiControllerState = navUiControllerState,
            navUiControllerGroups = fakeNavGroups,
            selectedRoute = selectedRoute.value,
            onItemClick = {
                selectedRoute.value = it.route
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                val navigationType = navUiControllerState.navigationType.collectAsState().value
                Column {
                    if (navigationType is NavigationOverlayType.Modal) {
                        val scope = rememberCoroutineScope()
                        Button(
                            onClick = {
                                scope.launch { navigationType.drawerState.open() }
                            },
                        ) {
                            Text(text = "Open")
                        }

                    }
                    Text(text = "content: ${selectedRoute.value}")
                }
            }

        }
    }
}
