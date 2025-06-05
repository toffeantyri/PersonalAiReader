package ai.personal.reader.domain.usecase

import ai.personal.reader.domain.model.Answer
import ai.personal.reader.domain.model.Question
import ai.personal.reader.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class GetChatHistoryUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(sessionId: String): Flow<List<Pair<Question, Answer>>> {
        return chatRepository.getChatHistory(sessionId)
    }
} 