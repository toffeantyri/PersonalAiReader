package ai.personal.reader.ui.components.home

import ai.personal.reader.domain.model.Document
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeState(
    val isLoading: Boolean = false,
    val documents: ImmutableList<Document> = persistentListOf(),
    val selectedDocument: Document? = null,
    val currentQuestion: String = "",
    val chatHistory: ImmutableList<Pair<String, String>> = persistentListOf(), // Simplified for now: Pair<QuestionText, AnswerText>
    val error: String? = null
) 