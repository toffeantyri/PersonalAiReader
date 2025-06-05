package ai.personal.reader.ui.components.home

import ai.personal.reader.domain.model.DocumentType

sealed interface HomeEvent {
    data class LoadDocuments(
        val documentPath: String,
        val documentName: String,
        val documentType: DocumentType
    ) : HomeEvent

    data class SelectDocument(val documentId: String) : HomeEvent
    data class UpdateQuestion(val newQuestion: String) : HomeEvent
    data class AskQuestion(val sessionId: String) : HomeEvent
    data object ClearChat : HomeEvent
    data object ErrorShown : HomeEvent
} 