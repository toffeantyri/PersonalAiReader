package ai.personal.reader.domain.usecase

import ai.personal.reader.domain.model.Document
import ai.personal.reader.domain.repository.DocumentRepository

class SaveDocumentUseCase(
    private val documentRepository: DocumentRepository
) {
    suspend operator fun invoke(document: Document) {
        documentRepository.saveDocument(document)
    }
} 