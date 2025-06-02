import ai.personal.reader.ui.components.root.IRootComponent
import ai.personal.reader.ui.components.root.RootComponentImpl
import ai.personal.reader.ui.screens.RootContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher

private const val SAVED_STATE_FILE_NAME = "SAVED_STATE_FILE_NAME"

fun main() {
    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher()

    val rootComponent: IRootComponent = RootComponentImpl(
        componentContext = DefaultComponentContext(
            lifecycle = lifecycle,
            stateKeeper = stateKeeper
        )
    )

    application {
        val windowState = rememberWindowState(width = 300.dp, height = 700.dp)

        Window(
            title = "Notebook AI Reader",
            state = windowState,
            onCloseRequest = { exitApplication() },
        ) {
            RootContent(rootComponent)
        }
    }
}
