package ai.personal.reader.data.database.entity

import ai.personal.reader.domain.model.DocumentType
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: DocumentType, // Will need a TypeConverter
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "file_path")
    val filePath: String,
    @ColumnInfo(name = "upload_date")
    val uploadDate: Long
) 