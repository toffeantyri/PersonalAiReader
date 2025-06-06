package ai.personal.reader.ui.screens

import ai.personal.reader.theme.AppTheme
import ai.personal.reader.ui.components.root.RootComponent
import ai.personal.reader.ui.components.root.RootEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val rootState by component.state.subscribeAsState()
    val settingsState by component.settingsComponent.state.subscribeAsState()

    AppTheme(isDarkTheme = settingsState.isDarkMode) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(
                        selected = rootState.stack.active.configuration is RootComponent.Config.Home,
                        onClick = { component.onEvent(RootEvent.HomeClick) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = rootState.stack.active.configuration is RootComponent.Config.Settings,
                        onClick = { component.onEvent(RootEvent.SettingsClick) },
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") }
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Children(
                    stack = rootState.stack,
                    modifier = Modifier.weight(1f),
                ) { child ->
                    when (val instance = child.instance) {
                        is RootComponent.Child.Home -> HomeContent(component = instance.component)
                        is RootComponent.Child.Settings -> SettingsContent(component = instance.component)
                    }
                }
            }
        }
    }
} 