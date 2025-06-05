package ai.personal.reader.domain.model

data class Question(
    val id: String,
    val sessionId: String,
    val text: String,
    val timestamp: Long,
    val documentId: String? = null // Optional: if the question is related to a specific document
) 