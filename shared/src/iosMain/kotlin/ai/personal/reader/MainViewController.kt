package ai.personal.reader

import ai.personal.reader.ui.components.root.RootComponentImpl
import ai.personal.reader.ui.screens.RootContent
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController =
    ComposeUIViewController {
        val lifecycle = remember { LifecycleRegistry() }
        val stateKeeper = remember { StateKeeperDispatcher() }

        val root = remember {
            RootComponentImpl(
                componentContext = DefaultComponentContext(
                    lifecycle = lifecycle,
                    stateKeeper = stateKeeper
                ),
                onExitHandle = { println("iOS Exit Handled") }
            )
        }

        RootContent(root)
    }