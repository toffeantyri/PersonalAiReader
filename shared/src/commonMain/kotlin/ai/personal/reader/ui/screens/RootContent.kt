package ai.personal.reader.ui.screens

import ai.personal.reader.presentation.chat.ui.ChatScreen
import ai.personal.reader.theme.AppTheme
import ai.personal.reader.ui.components.note.NotesListContent
import ai.personal.reader.ui.components.root.RootComponent
import ai.personal.reader.ui.components.root.RootEvent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState

@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val state by component.state.subscribeAsState()
    val settingsState by component.settingsComponent.state.subscribeAsState()
    val childStack = state.stack

    AppTheme(isDarkTheme = settingsState.isDarkMode) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                // Do not show bottom bar for Chat screen
                if (childStack.active.instance !is RootComponent.Child.Chat) {
                    NavigationBar {
                        NavigationBarItem(
                            selected = childStack.active.configuration is RootComponent.Config.Home,
                            onClick = { component.onEvent(RootEvent.HomeClick) },
                            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                            label = { Text("Home") }
                        )
                        NavigationBarItem(
                            selected = childStack.active.configuration is RootComponent.Config.NotesList,
                            onClick = { component.onEvent(RootEvent.NotesListClick) },
                            icon = { Icon(Icons.Default.List, contentDescription = "Заметки") },
                            label = { Text("Заметки") }
                        )
                        NavigationBarItem(
                            selected = childStack.active.configuration is RootComponent.Config.Settings,
                            onClick = { component.onEvent(RootEvent.SettingsClick) },
                            icon = {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "Settings"
                                )
                            },
                            label = { Text("Settings") }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Children(
                stack = childStack,
                modifier = Modifier.padding(paddingValues),
                animation = stackAnimation(slide())
            ) {
                when (val child = it.instance) {
                    is RootComponent.Child.Home -> HomeContent(child.component)
                    is RootComponent.Child.NotesList -> NotesListContent(child.component)
                    is RootComponent.Child.Settings -> SettingsContent(child.component)
                    is RootComponent.Child.Chat -> ChatScreen(child.component)
                }
            }
        }
    }
} 