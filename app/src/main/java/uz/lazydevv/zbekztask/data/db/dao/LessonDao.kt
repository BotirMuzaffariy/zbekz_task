package uz.lazydevv.zbekztask.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.lazydevv.zbekztask.data.db.entities.LessonEntity

@Dao
interface LessonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addLessons(lessons: List<LessonEntity>)

    @Query("select * from LessonEntity")
    fun getLessons(): Flow<List<LessonEntity>>
}