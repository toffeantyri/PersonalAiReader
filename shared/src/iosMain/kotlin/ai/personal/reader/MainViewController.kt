package ai.personal.reader

import ai.personal.reader.theme.AppTheme
import ai.personal.reader.ui.components.root.RootComponentImpl
import ai.personal.reader.ui.screens.RootContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import platform.UIKit.UIViewController

@Composable
fun App() {
    val lifecycle = remember { LifecycleRegistry() }
    val stateKeeper = remember { StateKeeperDispatcher() }

    val rootComponent = remember {
        RootComponentImpl(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper
            )
        )
    }

    AppTheme {
        RootContent(rootComponent)
    }
}

fun MainViewController(): UIViewController = ComposeUIViewController { App() }