package uz.lazydevv.zbekztask.data.datasources

import kotlinx.coroutines.flow.Flow
import uz.lazydevv.zbekztask.data.apiservices.LessonsApiService
import uz.lazydevv.zbekztask.data.models.requests.LessonRequestM
import uz.lazydevv.zbekztask.domain.datasources.LessonsDataSource

class LessonsDataSourceImpl(
    private val apiService: LessonsApiService
) : LessonsDataSource {

    override fun getLessons(): Flow<LessonRequestM> {
        return apiService.getLessons()
    }
}