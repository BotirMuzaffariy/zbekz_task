package uz.lazydevv.zbekztask.presentation.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.lazydevv.zbekztask.data.db.dao.LessonDao
import uz.lazydevv.zbekztask.data.repositories.LessonsRepoImpl
import uz.lazydevv.zbekztask.domain.datasources.LessonsDataSource
import uz.lazydevv.zbekztask.domain.repositories.LessonsRepo
import uz.lazydevv.zbekztask.presentation.utils.NetworkHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLessonsRepo(
        lessonsDataSource: LessonsDataSource,
        lessonDao: LessonDao,
        networkHelper: NetworkHelper
    ): LessonsRepo = LessonsRepoImpl(lessonsDataSource, lessonDao, networkHelper)
}