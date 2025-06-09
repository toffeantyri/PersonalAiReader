package ai.personal.reader.presentation.chat

import ai.personal.reader.domain.usecase.AskQuestionUseCase
import ai.personal.reader.presentation.chat.ChatComponent.ChatMessage
import ai.personal.reader.presentation.chat.ChatComponent.Model
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DefaultChatComponent(
    componentContext: ComponentContext,
    private val documentId: String?, // The ID of the selected document
    private val sessionId: String, // The ID of the current chat session
    private val askQuestionUseCase: AskQuestionUseCase,
    private val onFinished: () -> Unit,
) : ChatComponent, ComponentContext by componentContext {

    private val _model = MutableValue(Model())
    override val model: Value<Model> = _model

    private val scope = coroutineScope()

    override fun onBackClicked() {
        onFinished()
    }

    override fun onIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.UpdateInput -> {
                _model.update { it.copy(inputText = intent.text) }
            }

            ChatIntent.SendMessage -> {
                sendMessage()
            }
        }
    }

    private fun sendMessage() {
        val prompt = _model.value.inputText
        if (prompt.isBlank()) return

        val userMessage = ChatMessage(text = prompt, isFromUser = true)
        _model.update {
            it.copy(
                messages = it.messages + userMessage,
                inputText = "",
                isLoading = true
            )
        }

        scope.launch {
            try {
                val answer = askQuestionUseCase(
                    questionText = prompt,
                    documentId = documentId,
                    sessionId = sessionId
                )
                val aiMessage = ChatMessage(text = answer.text, isFromUser = false)
                _model.update {
                    it.copy(
                        messages = it.messages + aiMessage,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                // TODO: Handle error properly
                val errorMessage = ChatMessage(text = "Error: ${e.message}", isFromUser = false)
                _model.update {
                    it.copy(
                        messages = it.messages + errorMessage,
                        isLoading = false
                    )
                }
            }
        }
    }
} 