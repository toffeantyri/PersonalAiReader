package ai.personal.reader.ui.screens

import ai.personal.reader.ui.components.root.RootComponent
import ai.personal.reader.ui.components.root.RootEvent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = component.state.value.stack.active.configuration is RootComponent.Config.Home,
                    onClick = { component.onEvent(RootEvent.HomeClick) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = component.state.value.stack.active.configuration is RootComponent.Config.Settings,
                    onClick = { component.onEvent(RootEvent.SettingsClick) },
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { paddingValues ->
        Children(
            stack = component.state.value.stack,
            modifier = Modifier.padding(paddingValues),
            animation = stackAnimation(fade()),
        ) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Home -> HomeContent(component = instance.component)
                is RootComponent.Child.Settings -> SettingsContent(component = instance.component)
            }
        }
    }
} 