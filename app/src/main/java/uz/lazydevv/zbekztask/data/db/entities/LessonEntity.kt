package uz.lazydevv.zbekztask.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonEntity(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "lesson_id") val lessonId: Int,
    val title: String,
    val description: String,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String,
    @ColumnInfo(name = "video_url")
    val videoUrl: String
)
