package uz.lazydevv.zbekztask.presentation.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.lazydevv.zbekztask.data.apiservices.LessonsApiService
import uz.lazydevv.zbekztask.data.datasources.LessonsDataSourceImpl
import uz.lazydevv.zbekztask.domain.datasources.LessonsDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideLessonsDataSource(
        apiService: LessonsApiService
    ): LessonsDataSource = LessonsDataSourceImpl(apiService)
}