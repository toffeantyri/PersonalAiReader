package ai.personal.reader.ui.components.root

import ai.personal.reader.ui.components.settings.ISettingsComponent
import ai.personal.reader.ui.root.home.IHomeComponent
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface IRootComponent {
    val stack: Value<ChildStack<Config, Child>>

    fun onHomeClick()
    fun onSettingsClick()

    sealed class Child {
        data class Home(val component: IHomeComponent) : Child()
        data class Settings(val component: ISettingsComponent) : Child()
    }

    @Serializable
    sealed class Config {
        @Serializable
        data object Home : Config()

        @Serializable
        data object Settings : Config()
    }
} 