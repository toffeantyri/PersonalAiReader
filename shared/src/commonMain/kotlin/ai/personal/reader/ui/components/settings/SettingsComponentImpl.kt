package ai.personal.reader.ui.components.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value

class SettingsComponentImpl(componentContext: ComponentContext) : SettingsComponent,
    ComponentContext by componentContext {

    private val _state = MutableValue(SettingsState())
    override val state: Value<SettingsState> = _state

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ToggleDarkMode -> {
                _state.value = _state.value.copy(isDarkMode = event.enabled)
            }

            is SettingsEvent.ToggleNotifications -> {
                _state.value = _state.value.copy(notificationEnabled = event.enabled)
            }

            SettingsEvent.ErrorShown -> {
                _state.value = _state.value.copy(error = null)
            }
        }
    }
} 