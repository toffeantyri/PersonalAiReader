package ai.personal.reader.ui.components.root

import com.arkivanov.decompose.router.stack.ChildStack

data class RootState(
    val stack: ChildStack<RootComponent.Config, RootComponent.Child>
) 