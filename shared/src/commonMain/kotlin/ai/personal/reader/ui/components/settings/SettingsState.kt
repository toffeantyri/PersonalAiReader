package ai.personal.reader.ui.components.settings

data class SettingsState(
    val isDarkMode: Boolean = false,
    val notificationEnabled: Boolean = true,
    val appVersion: String = "1.0.0",
    val error: String? = null
) 