package ai.personal.reader.domain.model

data class Document(
    val id: String,
    val name: String,
    val type: DocumentType, // Changed from String to DocumentType
    val content: String, // The extracted text content of the document
    val filePath: String, // Path to the original file
    val uploadDate: Long
) 