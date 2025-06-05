package ai.personal.reader.domain.usecase

import ai.personal.reader.domain.model.Document
import ai.personal.reader.domain.model.DocumentType
import ai.personal.reader.domain.repository.DocumentRepository
import kotlinx.datetime.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class LoadDocumentUseCase(
    private val documentRepository: DocumentRepository // Keep for future use if needed, but not for direct saving here
) {
    // This function will need platform-specific implementation to read file content
    // For now, we'll assume content extraction is handled externally or in data layer
    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke(
        filePath: String,
        documentName: String,
        documentType: DocumentType
    ): Document {
        // In a real scenario, you'd read the file content here.
        // For simplicity, let's just create a dummy content for now.
        val dummyContent = "Content from $documentName ($filePath)"

        val newDocument = Document(
            id = Uuid.random().toString(),
            name = documentName,
            type = documentType,
            content = dummyContent,
            filePath = filePath,
            uploadDate = Clock.System.now().toEpochMilliseconds()
        )
        // documentRepository.saveDocument(newDocument) // Removed: Saving is a separate UseCase responsibility
        return newDocument
    }
} 