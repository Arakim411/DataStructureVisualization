package com.arakim.datastructurevisualization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arakim.dataStructureVisualization.ui.theme.AppTheme
import com.arakim.datastructurevisualization.ui.navigation.MainNavigation
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.FakeWindowSizeType
import com.arakim.datastructurevisualization.ui.util.windowSizeClass.WindowSizeType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                WindowSizeType {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        MainNavigation()
    }
}

@Preview(showBackground = true)
@Composable
fun AppLightThemePreview() {
    AppTheme(useDarkTheme = false) {
        FakeWindowSizeType {
            App()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppDarkThemePreview() {
    AppTheme(useDarkTheme = true) {
        FakeWindowSizeType {
            App()
        }
    }
}
