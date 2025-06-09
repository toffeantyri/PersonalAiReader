package ai.personal.reader.di

import ai.personal.reader.presentation.chat.ChatComponent
import ai.personal.reader.presentation.chat.DefaultChatComponent
import com.arkivanov.decompose.ComponentContext
import org.koin.dsl.module

val viewModelModule = module {
    factory<ChatComponent> { (componentContext: ComponentContext, documentId: String?, sessionId: String, onFinished: () -> Unit) ->
        DefaultChatComponent(
            componentContext = componentContext,
            documentId = documentId,
            sessionId = sessionId,
            askQuestionUseCase = get(),
            onFinished = onFinished
        )
    }
} 