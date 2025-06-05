package ai.personal.reader.ui.components.settings

sealed interface SettingsEvent {
    data class ToggleDarkMode(val enabled: Boolean) : SettingsEvent
    data class ToggleNotifications(val enabled: Boolean) : SettingsEvent
    data object ErrorShown : SettingsEvent
} 