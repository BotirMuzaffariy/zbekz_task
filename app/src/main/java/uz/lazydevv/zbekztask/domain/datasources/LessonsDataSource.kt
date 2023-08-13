package uz.lazydevv.zbekztask.domain.datasources

import kotlinx.coroutines.flow.Flow
import uz.lazydevv.zbekztask.data.models.requests.LessonRequestM

interface LessonsDataSource {

    fun getLessons(): Flow<LessonRequestM>
}