package ai.personal.reader.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {
    @Insert
    suspend fun insert(document: DocumentEntity): Long

    @Query("SELECT * FROM DocumentEntity ORDER BY createdAt DESC")
    fun getAll(): Flow<List<DocumentEntity>>

    @Query("SELECT * FROM DocumentEntity WHERE id = :id")
    suspend fun getById(id: Long): DocumentEntity?

    @Query("DELETE FROM DocumentEntity WHERE id = :id")
    suspend fun deleteById(id: Long)
} 