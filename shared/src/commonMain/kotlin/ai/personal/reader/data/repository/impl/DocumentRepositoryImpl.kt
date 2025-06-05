package ai.personal.reader.data.repository.impl

import ai.personal.reader.data.database.dao.DocumentDao
import ai.personal.reader.data.database.entity.DocumentEntity
import ai.personal.reader.domain.model.Document
import ai.personal.reader.domain.repository.DocumentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DocumentRepositoryImpl(
    private val documentDao: DocumentDao
) : DocumentRepository {

    override suspend fun saveDocument(document: Document) {
        documentDao.insertDocument(document.toEntity())
    }

    override suspend fun getAllDocuments(): Flow<List<Document>> {
        return documentDao.getAllDocuments().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getDocumentById(id: String): Document? {
        return documentDao.getDocumentById(id)?.toDomain()
    }

    override suspend fun deleteDocument(id: String) {
        val documentEntity = documentDao.getDocumentById(id)
        documentEntity?.let { documentDao.deleteDocument(it) }
    }

    private fun Document.toEntity(): DocumentEntity {
        return DocumentEntity(
            id = this.id,
            name = this.name,
            type = this.type,
            content = this.content,
            filePath = this.filePath,
            uploadDate = this.uploadDate
        )
    }

    private fun DocumentEntity.toDomain(): Document {
        return Document(
            id = this.id,
            name = this.name,
            type = this.type,
            content = this.content,
            filePath = this.filePath,
            uploadDate = this.uploadDate
        )
    }
} 