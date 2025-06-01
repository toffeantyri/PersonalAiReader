import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension

private const val SAVED_STATE_FILE_NAME = "SAVED_STATE_FILE_NAME"

fun main() {
//    val lifecycle = LifecycleRegistry()
//    val stateKeeper = StateKeeperDispatcher() //todo need container
//
//    initDataStore()
//    initKoin(enableNetworkLogs = true)
//
//    var onExit: () -> Unit = {}
//
//    val root = runOnUiThread {
//        RootBottomComponentImpl(
//            componentContext =
//            DefaultComponentContext(
//                lifecycle,
//                stateKeeper
//            ), onExit = onExit
//        )
//    }

    application {
        val windowState = rememberWindowState(width = 300.dp, height = 700.dp)


//        onExit = this::exitApplication

        Window(
            title = "Сарафан Доставка Desktop App",
            state = windowState,
            onCloseRequest = { },
        ) {
            window.minimumSize = Dimension(350, 600)
//            App(root, modifier = Modifier)

            Surface {
                Text(text = "any")
            }

        }
    }
}
