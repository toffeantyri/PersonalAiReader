package ai.personal.reader.ui.components.root

import ai.personal.reader.ui.components.home.HomeComponent
import ai.personal.reader.ui.components.home.HomeComponentImpl
import ai.personal.reader.ui.components.note.NotesListComponent
import ai.personal.reader.ui.components.note.NotesListComponentImpl
import ai.personal.reader.ui.components.settings.SettingsComponent
import ai.personal.reader.ui.components.settings.SettingsComponentImpl
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.instancekeeper.getOrCreate

class RootComponentImpl(
    componentContext: ComponentContext,
    private val onExitHandle: () -> Unit
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<RootComponent.Config>()

    private val _childStack = childStack(
        source = navigation,
        initialStack = { listOf(RootComponent.Config.Home) },
        handleBackButton = false,
        childFactory = ::childFactory,
        serializer = RootComponent.Config.serializer()
    )

    override val state: Value<RootState> = _childStack.map { RootState(stack = it) }

    override val settingsComponent: SettingsComponent = instanceKeeper.getOrCreate {
        SettingsComponentImpl(componentContext)
    }

    override fun onEvent(event: RootEvent) {
        when (event) {
            RootEvent.HomeClick -> {
                navigation.popWhile { it != RootComponent.Config.Home }
                navigation.replaceCurrent(RootComponent.Config.Home)
            }

            RootEvent.SettingsClick -> {
                navigation.popWhile { it != RootComponent.Config.Settings }
                navigation.replaceCurrent(RootComponent.Config.Settings)
            }

            RootEvent.NotesListClick -> {
                navigation.popWhile { it != RootComponent.Config.NotesList }
                navigation.replaceCurrent(RootComponent.Config.NotesList)
            }
        }
    }

    override fun onBackClicked() {
        if (_childStack.value.active.configuration != RootComponent.Config.Home) {
            navigation.pop()
        } else {
            onExitHandle()
        }
    }

    private fun childFactory(
        config: RootComponent.Config,
        componentContext: ComponentContext
    ): RootComponent.Child = when (config) {
        is RootComponent.Config.Home -> RootComponent.Child.Home(homeComponent(componentContext))
        is RootComponent.Config.Settings -> RootComponent.Child.Settings(
            settingsComponent
        )
        is RootComponent.Config.NotesList -> RootComponent.Child.NotesList(
            notesListComponent(
                componentContext,
                onNoteSelected = { noteId ->
                    // Handle note selection (e.g., navigate to note detail screen)
                    println("Note selected: $noteId")
                    navigation.pop()
                },
                onAddNoteRequested = {
                    // Handle add note request (e.g., navigate to create new note screen)
                    println("Add new note requested")
                    navigation.pop()
                }
            )
        )
    }

    private fun homeComponent(componentContext: ComponentContext): HomeComponent =
        HomeComponentImpl(componentContext)

    private fun settingsComponent(componentContext: ComponentContext): SettingsComponent =
        SettingsComponentImpl(componentContext)

    private fun notesListComponent(
        componentContext: ComponentContext,
        onNoteSelected: (noteId: String) -> Unit,
        onAddNoteRequested: () -> Unit
    ): NotesListComponent = NotesListComponentImpl(
        componentContext = componentContext,
        onNoteSelected = onNoteSelected,
        onAddNoteRequested = onAddNoteRequested
    )
} 