package ai.personal.reader.ui.components.root

import ai.personal.reader.ui.components.settings.SettingsComponentImpl
import ai.personal.reader.ui.root.home.HomeComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value

class RootComponentImpl(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, IRootComponent {

    private val navigation = StackNavigation<IRootComponent.Config>()
    override val stack: Value<ChildStack<IRootComponent.Config, IRootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = IRootComponent.Config.Home,
        handleBackButton = true,
        childFactory = ::createChild,
        serializer = IRootComponent.Config.serializer()
    )

    private fun createChild(
        config: IRootComponent.Config,
        componentContext: ComponentContext,
    ): IRootComponent.Child =
        when (config) {
            is IRootComponent.Config.Home -> IRootComponent.Child.Home(
                HomeComponentImpl(
                    componentContext
                )
            )

            is IRootComponent.Config.Settings -> IRootComponent.Child.Settings(
                SettingsComponentImpl(
                    componentContext
                )
            )
        }

    override fun onHomeClick() {
        navigation.bringToFront(IRootComponent.Config.Home)
    }

    override fun onSettingsClick() {
        navigation.bringToFront(IRootComponent.Config.Settings)
    }
} 