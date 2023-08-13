package uz.lazydevv.zbekztask.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.lazydevv.zbekztask.data.models.LessonM

interface LessonsRepo {

    fun fetchLessons(): Flow<List<LessonM>>

    fun getLocalLessons(): Flow<List<LessonM>>
}