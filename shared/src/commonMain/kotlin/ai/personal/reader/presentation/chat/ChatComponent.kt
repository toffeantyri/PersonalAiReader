package ai.personal.reader.presentation.chat

import com.arkivanov.decompose.value.Value

interface ChatComponent {

    val model: Value<Model>

    fun onIntent(intent: ChatIntent)
    fun onBackClicked()

    data class Model(
        val messages: List<ChatMessage> = emptyList(),
        val inputText: String = "",
        val isLoading: Boolean = false
    )

    data class ChatMessage(
        val text: String,
        val isFromUser: Boolean
    )
}

sealed interface ChatIntent {
    data class UpdateInput(val text: String) : ChatIntent
    data object SendMessage : ChatIntent
} 