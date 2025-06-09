package ai.personal.reader.ui.components.root

import ai.personal.reader.domain.usecase.CreateNewChatSessionUseCase
import ai.personal.reader.presentation.chat.ChatComponent
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
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class RootComponentImpl(
    componentContext: ComponentContext,
    private val onExitHandle: () -> Unit
) : RootComponent, ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<RootComponent.Config>()
    private val scope = coroutineScope()
    private val createNewChatSessionUseCase: CreateNewChatSessionUseCase = get()

    init {
        backHandler.register(BackCallback(onBack = ::onBackClicked))
    }

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
            is RootEvent.HomeClick -> {
                navigation.popWhile { it != RootComponent.Config.Home }
                navigation.replaceCurrent(RootComponent.Config.Home)
            }

            is RootEvent.SettingsClick -> {
                navigation.popWhile { it != RootComponent.Config.Settings }
                navigation.replaceCurrent(RootComponent.Config.Settings)
            }

            is RootEvent.NotesListClick -> {
                navigation.popWhile { it != RootComponent.Config.NotesList }
                navigation.replaceCurrent(RootComponent.Config.NotesList)
            }

            is RootEvent.ChatClick -> {
                scope.launch {
                    val sessionId = createNewChatSessionUseCase()
                    navigation.push(RootComponent.Config.Chat(event.documentId, sessionId))
                }
            }
        }
    }

    override fun onBackClicked() {
        if (_childStack.value.items.size > 1) {
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
                    // For now, let's just navigate to chat with the noteId as context
                    onEvent(RootEvent.ChatClick(documentId = noteId))
                },
                onAddNoteRequested = {
                    // Handle add note request (e.g., navigate to create new note screen)
                    println("Add new note requested")
                    navigation.pop()
                }
            )
        )

        is RootComponent.Config.Chat -> RootComponent.Child.Chat(
            chatComponent(componentContext, config.documentId, config.sessionId, navigation::pop)
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

    private fun chatComponent(
        componentContext: ComponentContext,
        documentId: String?,
        sessionId: String,
        onFinished: () -> Unit
    ): ChatComponent {
        return get { parametersOf(componentContext, documentId, sessionId, onFinished) }
    }
} 