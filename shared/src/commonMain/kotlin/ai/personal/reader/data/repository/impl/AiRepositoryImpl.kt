package ai.personal.reader.data.repository.impl

import ai.personal.reader.data.remote.GroqApi
import ai.personal.reader.data.remote.dto.GroqRequest
import ai.personal.reader.data.remote.dto.Message
import ai.personal.reader.domain.repository.AiRepository

class AiRepositoryImpl(private val groqApi: GroqApi) : AiRepository {

    override suspend fun getAnswer(prompt: String, context: String): String {
        val request = GroqRequest(
            messages = listOf(
                Message(
                    role = "system",
                    content = "You are a helpful assistant. The user provides you with a document context, and you answer questions based on it."
                ),
                Message(
                    role = "user",
                    content = "Context: ###\n$context\n###\n\nQuestion: $prompt"
                )
            ),
            model = "llama3-8b-8192" // Or any other model you prefer
        )
        val response = groqApi.getCompletion(request)
        return response.choices.firstOrNull()?.message?.content ?: "No answer from AI"
    }
} 