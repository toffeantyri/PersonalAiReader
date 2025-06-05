package ai.personal.reader.data.database.dao

import ai.personal.reader.data.database.entity.AnswerEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswer(answer: AnswerEntity)

    @Update
    suspend fun updateAnswer(answer: AnswerEntity)

    @Query("SELECT * FROM answers WHERE session_id = :sessionId ORDER BY timestamp ASC")
    fun getAnswersBySessionId(sessionId: String): Flow<List<AnswerEntity>>

    @Query("SELECT * FROM answers WHERE question_id = :questionId")
    suspend fun getAnswerByQuestionId(questionId: String): AnswerEntity?
} 