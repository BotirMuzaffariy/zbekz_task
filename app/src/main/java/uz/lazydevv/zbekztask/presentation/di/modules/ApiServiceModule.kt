package uz.lazydevv.zbekztask.presentation.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.lazydevv.zbekztask.data.apiservices.LessonsApiService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideLessonsApiService(
        retrofit: Retrofit
    ): LessonsApiService {
        return retrofit.create(LessonsApiService::class.java)
    }
}