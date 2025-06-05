package ai.personal.reader.domain.usecase

import ai.personal.reader.domain.repository.ChatRepository

class CreateNewChatSessionUseCase(
    private val chatRepository: ChatRepository
) {
    suspend operator fun invoke(): String {
        return chatRepository.createNewChatSession()
    }
} 