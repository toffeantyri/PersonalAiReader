package ai.personal.reader.ui.components.root

import ai.personal.reader.ui.components.home.HomeComponent
import ai.personal.reader.ui.components.settings.SettingsComponent
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RootComponent {
    val state: Value<RootState>
    val settingsComponent: SettingsComponent

    fun onEvent(event: RootEvent)

    sealed class Child {
        data class Home(val component: HomeComponent) : Child()
        data class Settings(val component: SettingsComponent) : Child()
    }

    @Serializable
    sealed class Config {
        @Serializable
        data object Home : Config()

        @Serializable
        data object Settings : Config()
    }
} 