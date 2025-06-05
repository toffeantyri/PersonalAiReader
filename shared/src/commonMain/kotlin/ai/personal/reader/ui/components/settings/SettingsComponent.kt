package ai.personal.reader.ui.components.settings

import com.arkivanov.decompose.value.Value

interface SettingsComponent {
    val state: Value<SettingsState>

    fun onEvent(event: SettingsEvent)
} 