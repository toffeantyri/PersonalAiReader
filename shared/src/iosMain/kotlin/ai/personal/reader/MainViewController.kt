package ai.personal.reader

import ai.personal.reader.theme.AppTheme
import ai.personal.reader.ui.screens.DefaultScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.ComposeUIViewController

@Composable
fun App() {
    AppTheme {
        DefaultScreen()
    }
}

fun MainViewController() = ComposeUIViewController { App() }