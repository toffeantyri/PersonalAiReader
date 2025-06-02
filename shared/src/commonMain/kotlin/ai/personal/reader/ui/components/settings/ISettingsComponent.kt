package ai.personal.reader.ui.components.settings

import com.arkivanov.decompose.value.Value

interface ISettingsComponent {
    val isDarkMode: Value<Boolean>
    fun onDarkModeToggle()
} 