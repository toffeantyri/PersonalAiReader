package ai.personal.reader.domain.repository

interface AiRepository {
    suspend fun getAnswer(prompt: String, context: String): String
} 