package ai.personal.reader.ui.components.root

import ai.personal.reader.presentation.chat.ChatComponent
import ai.personal.reader.ui.components.home.HomeComponent
import ai.personal.reader.ui.components.note.NotesListComponent
import ai.personal.reader.ui.components.settings.SettingsComponent
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RootComponent {
    val state: Value<RootState>
    val settingsComponent: SettingsComponent

    fun onEvent(event: RootEvent)
    fun onBackClicked()

    sealed class Child {
        data class Home(val component: HomeComponent) : Child()
        data class Settings(val component: SettingsComponent) : Child()
        data class NotesList(val component: NotesListComponent) : Child()
        data class Chat(val component: ChatComponent) : Child()
    }

    @Serializable
    sealed class Config {
        @Serializable
        data object Home : Config()

        @Serializable
        data object Settings : Config()

        @Serializable
        data object NotesList : Config()

        @Serializable
        data class Chat(val documentId: String?, val sessionId: String) : Config()
    }
}

sealed interface RootEvent {
    data object HomeClick : RootEvent
    data object SettingsClick : RootEvent
    data object NotesListClick : RootEvent
    data class ChatClick(val documentId: String?) : RootEvent
} 