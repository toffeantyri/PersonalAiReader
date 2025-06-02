package ai.personal.reader.ui.components.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class SettingsComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, ISettingsComponent {
    private val _isDarkMode = MutableValue(false)
    override val isDarkMode: Value<Boolean> = _isDarkMode

    override fun onDarkModeToggle() {
        _isDarkMode.value = !_isDarkMode.value
    }
} 