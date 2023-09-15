package com.arakim.datastructurevisualization

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arakim.dataStructureVisualization.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Hello World!")
    }
}

@Preview(showBackground = true)
@Composable
fun AppLightThemePreview() {
    AppTheme(useDarkTheme = false) {
        App()
    }
}

@Preview(showBackground = true)
@Composable
fun AppDarkThemePreview() {
    AppTheme(useDarkTheme = true) {
        App()
    }
}
