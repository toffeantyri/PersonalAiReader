package ai.personal.reader.domain.repository

import ai.personal.reader.domain.model.Document
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {
    suspend fun saveDocument(document: Document)
    suspend fun getAllDocuments(): Flow<List<Document>>
    suspend fun getDocumentById(id: String): Document?
    suspend fun deleteDocument(id: String)
} 