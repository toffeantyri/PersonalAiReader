package ai.personal.reader.ui.screens

import ai.personal.reader.ui.components.root.IRootComponent
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
    component: IRootComponent,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = component.stack.value.active.configuration is IRootComponent.Config.Home,
                    onClick = component::onHomeClick,
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = component.stack.value.active.configuration is IRootComponent.Config.Settings,
                    onClick = component::onSettingsClick,
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") }
                )
            }
        }
    ) { paddingValues ->
        Children(
            stack = component.stack,
            modifier = Modifier.padding(paddingValues),
            animation = stackAnimation(fade()),
        ) { child ->
            when (val instance = child.instance) {
                is IRootComponent.Child.Home -> HomeContent(component = instance.component)
                is IRootComponent.Child.Settings -> SettingsContent(component = instance.component)
            }
        }
    }
} 