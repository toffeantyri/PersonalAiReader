package ai.personal.reader.data.repository.impl

import ai.personal.reader.data.database.dao.AnswerDao
import ai.personal.reader.data.database.dao.QuestionDao
import ai.personal.reader.data.database.entity.AnswerEntity
import ai.personal.reader.data.database.entity.QuestionEntity
import ai.personal.reader.domain.model.Answer
import ai.personal.reader.domain.model.Question
import ai.personal.reader.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ChatRepositoryImpl(
    private val questionDao: QuestionDao,
    private val answerDao: AnswerDao,
    // private val aiService: AIService // TODO: Add actual AI service interface here
) : ChatRepository {

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun askQuestion(question: Question, documentContent: String?): Answer {
        // TODO: Implement actual AI API call here
        // For now, return a dummy answer
        val answerText = "This is a dummy answer to: \"${question.text}\"\n" +
                if (documentContent != null) "(Based on document content provided)" else ""

        val answer = Answer(
            id = Uuid.random().toString(),
            sessionId = question.sessionId,
            questionId = question.id,
            text = answerText,
            timestamp = Clock.System.now().toEpochMilliseconds(),
            sourceDocumentId = question.documentId
        )
        return answer
    }

    override suspend fun saveQuestion(question: Question) {
        questionDao.insertQuestion(question.toEntity())
    }

    override suspend fun saveAnswer(answer: Answer) {
        answerDao.insertAnswer(answer.toEntity())
    }

    override suspend fun getChatHistory(sessionId: String): Flow<List<Pair<Question, Answer>>> {
        return combine(
            questionDao.getQuestionsBySessionId(sessionId)
                .map { it.map { entity -> entity.toDomain() } },
            answerDao.getAnswersBySessionId(sessionId)
                .map { it.map { entity -> entity.toDomain() } }
        ) { questions, answers ->
            questions.map { question ->
                val answer = answers.firstOrNull { it.questionId == question.id }
                question to (answer ?: Answer(
                    "",
                    question.sessionId,
                    question.id,
                    "",
                    0L
                )) // Provide a default empty answer if not found
            }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createNewChatSession(): String {
        // For simplicity, just return a new UUID as a session ID
        return Uuid.random().toString()
    }

    private fun Question.toEntity(): QuestionEntity {
        return QuestionEntity(
            id = this.id,
            sessionId = this.sessionId,
            text = this.text,
            timestamp = this.timestamp,
            documentId = this.documentId
        )
    }

    private fun QuestionEntity.toDomain(): Question {
        return Question(
            id = this.id,
            sessionId = this.sessionId,
            text = this.text,
            timestamp = this.timestamp,
            documentId = this.documentId
        )
    }

    private fun Answer.toEntity(): AnswerEntity {
        return AnswerEntity(
            id = this.id,
            sessionId = this.sessionId,
            questionId = this.questionId,
            text = this.text,
            timestamp = this.timestamp,
            sourceDocumentId = this.sourceDocumentId
        )
    }

    private fun AnswerEntity.toDomain(): Answer {
        return Answer(
            id = this.id,
            sessionId = this.sessionId,
            questionId = this.questionId,
            text = this.text,
            timestamp = this.timestamp,
            sourceDocumentId = this.sourceDocumentId
        )
    }
} 