package ai.personal.reader.domain.model

data class Answer(
    val id: String,
    val sessionId: String,
    val questionId: String,
    val text: String,
    val timestamp: Long,
    val sourceDocumentId: String? = null // Optional: if the answer is sourced from a specific document
) 