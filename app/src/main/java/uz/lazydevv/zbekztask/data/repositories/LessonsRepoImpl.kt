package uz.lazydevv.zbekztask.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import uz.lazydevv.zbekztask.data.db.dao.LessonDao
import uz.lazydevv.zbekztask.data.models.LessonM
import uz.lazydevv.zbekztask.data.preferences.AppSharedPrefs
import uz.lazydevv.zbekztask.domain.datasources.LessonsDataSource
import uz.lazydevv.zbekztask.domain.mappers.toLessonEntityList
import uz.lazydevv.zbekztask.domain.mappers.toLessonM
import uz.lazydevv.zbekztask.domain.repositories.LessonsRepo
import uz.lazydevv.zbekztask.presentation.utils.AppConstants.DEFAULT_OPENED_LESSONS_COUNT
import uz.lazydevv.zbekztask.presentation.utils.NetworkHelper

class LessonsRepoImpl(
    private val lessonsDataSource: LessonsDataSource,
    private val lessonDao: LessonDao,
    private val networkHelper: NetworkHelper,
    private val sharedPrefs: AppSharedPrefs
) : LessonsRepo {

    override fun fetchLessons(): Flow<List<LessonM>> {
        if (networkHelper.isNetworkConnected()) {
            return lessonsDataSource.getLessons()
                .map {
                    it.lessons?.mapIndexed { index, lessonItem ->
                        lessonItem
                            .toLessonM()
                            .copy(isOpen = if (sharedPrefs.isPaid) true else index < DEFAULT_OPENED_LESSONS_COUNT)
                    } ?: emptyList()
                }
                .onEach { list ->
                    lessonDao.addLessons(list.toLessonEntityList())
                }
        } else {
            return lessonDao.getLessons().map { list ->
                if (list.isEmpty()) {
                    // it means the user enter the app first time and there is no internet
                    throw Exception("No internet connection!")
                }

                list.map {
                    it.toLessonM()
                }
            }
        }
    }

    override fun getLocalLessons(): Flow<List<LessonM>> {
        return lessonDao.getLessons().map { list ->
            list.map {
                it.toLessonM()
            }
        }
    }
}