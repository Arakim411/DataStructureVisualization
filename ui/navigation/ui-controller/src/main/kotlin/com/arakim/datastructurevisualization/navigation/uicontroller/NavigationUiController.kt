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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.arakim.datastructurevisualization.navigation.uicontroller.controllers.ModalNavigationDrawer
import com.arakim.datastructurevisualization.navigation.uicontroller.controllers.NavigationDrawer
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

            WindowSizeType.Expanded -> NavigationDrawer(
                navigationUiControllerState = navigationUiControllerState,
                navigationGroups = navUiControllerGroups,
                onItemClick = onItemClick,
                selectedRoute = selectedRoute,
                content = content,
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
private fun NavUiControllerCompactMediumPreview(
    @PreviewParameter(
        NavGroupsPreviewParameterProvider::class,
        limit = 2,
    ) navGroups: ImmutableList<NavUiControllerGroup>,
) {

    FakeWindowSizeType(width = WindowSizeType.Medium) {
        NavigationUiControllerPreview(navGroups = navGroups)
    }
}

@Composable
@Preview(
    showBackground = true
)
private fun NavUiControllerExpandedPreview(
    @PreviewParameter(
        NavGroupsPreviewParameterProvider::class,
        limit = 2,
    ) navGroups: ImmutableList<NavUiControllerGroup>,
) {

    FakeWindowSizeType(width = WindowSizeType.Expanded) {
        NavigationUiControllerPreview(navGroups = navGroups)
    }
}

@Composable
private fun NavigationUiControllerPreview(navGroups: ImmutableList<NavUiControllerGroup>) {

    val navUiControllerState = rememberNavUiControllerState()
    val selectedRoute = remember { mutableStateOf(navGroups[0].items[0].route) }

    NavigationUiController(
        navigationUiControllerState = navUiControllerState,
        navUiControllerGroups = navGroups,
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