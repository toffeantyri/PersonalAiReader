package ai.personal.reader.ui.components.root

sealed interface RootEvent {
    data object HomeClick : RootEvent
    data object SettingsClick : RootEvent
    data object NotesListClick : RootEvent
} 