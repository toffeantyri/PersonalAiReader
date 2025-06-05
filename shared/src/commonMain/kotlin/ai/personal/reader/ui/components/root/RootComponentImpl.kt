package ai.personal.reader.ui.components.root

import ai.personal.reader.ui.components.home.HomeComponent
import ai.personal.reader.ui.components.home.HomeComponentImpl
import ai.personal.reader.ui.components.settings.SettingsComponent
import ai.personal.reader.ui.components.settings.SettingsComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootComponent.Config>()

    private val _childStack = childStack(
        source = navigation,
        initialStack = { listOf(RootComponent.Config.Home) },
        handleBackButton = true,
        childFactory = ::childFactory,
        serializer = RootComponent.Config.serializer() // Re-enabling serializer
    )

    override val state: Value<RootState> = _childStack.map { RootState(stack = it) }

    override fun onEvent(event: RootEvent) {
        when (event) {
            RootEvent.HomeClick -> {
                navigation.pop()
            }

            RootEvent.SettingsClick -> {
                navigation.pushNew(RootComponent.Config.Settings)
            }
        }
    }

    private fun childFactory(
        config: RootComponent.Config,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        is RootComponent.Config.Home -> RootComponent.Child.Home(homeComponent(componentContext))
        is RootComponent.Config.Settings -> RootComponent.Child.Settings(
            settingsComponent(
                componentContext
            )
        )
    }

    private fun homeComponent(componentContext: ComponentContext): HomeComponent =
        HomeComponentImpl(componentContext)

    private fun settingsComponent(componentContext: ComponentContext): SettingsComponent =
        SettingsComponentImpl(componentContext)
} 