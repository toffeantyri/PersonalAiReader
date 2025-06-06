import ai.personal.reader.runOnUiThread
import ai.personal.reader.theme.AppTheme
import ai.personal.reader.ui.components.root.RootComponent
import ai.personal.reader.ui.components.root.RootComponentImpl
import ai.personal.reader.ui.screens.RootContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.lifecycle.stop
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher

private const val SAVED_STATE_FILE_NAME = "SAVED_STATE_FILE_NAME"

fun main() {
    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher()

    val rootComponent: RootComponent = runOnUiThread {
        RootComponentImpl(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper
            )
        )
    }

    application {
        val windowState = rememberWindowState(width = 300.dp, height = 700.dp)

        Window(
            title = "Notebook AI Reader",
            state = windowState,
            onCloseRequest = { exitApplication() },
        ) {
            DisposableEffect(lifecycle) {
                lifecycle.resume()
                onDispose { lifecycle.stop() }
            }
            val settingsState by rootComponent.settingsComponent.state.subscribeAsState()
            AppTheme(isDarkTheme = settingsState.isDarkMode) {
                RootContent(rootComponent)
            }
        }
    }
}
