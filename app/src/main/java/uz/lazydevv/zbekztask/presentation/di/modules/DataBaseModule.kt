package uz.lazydevv.zbekztask.presentation.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.lazydevv.zbekztask.data.db.AppDb
import uz.lazydevv.zbekztask.data.db.dao.LessonDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    private const val DB_NAME = "lessons_db"

    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext context: Context
    ): AppDb {
        return Room.databaseBuilder(context, AppDb::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideLessonDao(
        appDb: AppDb
    ): LessonDao {
        return appDb.lessonDao()
    }
}