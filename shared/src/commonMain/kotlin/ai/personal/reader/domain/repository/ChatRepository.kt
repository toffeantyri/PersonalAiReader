package ai.personal.reader.domain.repository

import ai.personal.reader.domain.model.Answer
import ai.personal.reader.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun askQuestion(question: Question, documentContent: String?): Answer
    suspend fun saveQuestion(question: Question)
    suspend fun saveAnswer(answer: Answer)
    suspend fun getChatHistory(sessionId: String): Flow<List<Pair<Question, Answer>>>
    suspend fun createNewChatSession(): String
} 