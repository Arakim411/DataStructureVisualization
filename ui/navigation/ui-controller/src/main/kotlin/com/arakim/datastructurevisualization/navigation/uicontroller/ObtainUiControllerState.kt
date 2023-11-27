package com.arakim.datastructurevisualization.navigation.uicontroller

import androidx.compose.material3.DrawerValue.Closed
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arakim.datastructurevisualization.navigation.uicontroller.UiControllerType.ModalDrawer
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.ImpactProperty.Width
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeDelimiter
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Compact
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Expanded
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType.Medium

@Composable
fun ObtainUiControllerState(
    content: @Composable (state: NavigationUiControllerState) -> Unit,
) {
    WindowSizeDelimiter(impactedProperty = Width) { windowSizeType ->

        val state = when (windowSizeType) {
            Compact, Medium -> {
                val drawerState = rememberDrawerState(initialValue = Closed)
                remember {
                    NavigationUiControllerState(ModalDrawer(drawerState))
                }
            }

            Expanded -> {
                remember {
                    NavigationUiControllerState(UiControllerType.Drawer)
                }
            }
        }
        content(state)
    }
}