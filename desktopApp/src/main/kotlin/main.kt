import ai.personal.reader.ui.components.root.RootComponentImpl
import ai.personal.reader.ui.screens.RootContent
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import kotlin.system.exitProcess

fun main() = application {
    val lifecycle = remember { LifecycleRegistry() }
    val stateKeeper = remember { StateKeeperDispatcher() }

    val root = remember {
        RootComponentImpl(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper
            ),
            onExitHandle = { exitProcess(0) }
        )
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Notebook AI Reader",
        state = WindowState(size = DpSize(1200.dp, 800.dp))
    ) {
        RootContent(root)
    }
}
