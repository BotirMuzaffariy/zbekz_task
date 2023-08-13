package uz.lazydevv.zbekztask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.lazydevv.zbekztask.data.db.dao.LessonDao
import uz.lazydevv.zbekztask.data.db.entities.LessonEntity

@Database(entities = [LessonEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun lessonDao(): LessonDao
}