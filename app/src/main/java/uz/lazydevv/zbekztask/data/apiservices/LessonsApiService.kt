package uz.lazydevv.zbekztask.data.apiservices

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import uz.lazydevv.zbekztask.data.models.requests.LessonRequestM

interface LessonsApiService {

    @GET("lessons")
    fun getLessons(): Flow<LessonRequestM>
}