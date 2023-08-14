package uz.lazydevv.zbekztask.presentation.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedFlowModule {

    @Provides
    @Singleton
    fun provideFetchLessonSharedFlow(): MutableSharedFlow<Boolean> {
        return MutableSharedFlow(1)
    }
}