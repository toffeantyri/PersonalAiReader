package ai.personal.reader.data.database

import ai.personal.reader.data.database.dao.AnswerDao
import ai.personal.reader.data.database.dao.DocumentDao
import ai.personal.reader.data.database.dao.QuestionDao
import ai.personal.reader.data.database.entity.AnswerEntity
import ai.personal.reader.data.database.entity.DocumentEntity
import ai.personal.reader.data.database.entity.QuestionEntity
import ai.personal.reader.domain.model.DocumentType
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [
        DocumentEntity::class,
        QuestionEntity::class,
        AnswerEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(AppDatabase.Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun documentDao(): DocumentDao
    abstract fun questionDao(): QuestionDao
    abstract fun answerDao(): AnswerDao

    class Converters {
        @TypeConverter
        fun fromDocumentType(type: DocumentType): String {
            return type.name
        }

        @TypeConverter
        fun toDocumentType(name: String): DocumentType {
            return DocumentType.valueOf(name)
        }
    }
} 