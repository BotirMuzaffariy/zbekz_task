package uz.lazydevv.zbekztask.domain.mappers

import uz.lazydevv.zbekztask.data.db.entities.LessonEntity
import uz.lazydevv.zbekztask.data.models.LessonM
import uz.lazydevv.zbekztask.data.models.requests.LessonRequestM

fun LessonRequestM.toLessonMList(): List<LessonM> {
    return this.lessons?.map {
        LessonM(
            id = it?.id ?: -1,
            title = it?.name ?: "",
            description = it?.description ?: "",
            thumbnailUrl = it?.thumbnail ?: "",
            videoUrl = it?.video_url ?: ""
        )
    } ?: emptyList()
}

fun List<LessonM>.toLessonEntityList(): List<LessonEntity> {
    return this.mapIndexed { index, lesson ->
        LessonEntity(
            id = index,
            lessonId = lesson.id,
            title = lesson.title,
            description = lesson.description,
            thumbnailUrl = lesson.thumbnailUrl,
            videoUrl = lesson.videoUrl
        )
    }
}

fun LessonEntity.toLessonM(): LessonM {
    return LessonM(
        id = this.lessonId,
        title = this.title,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        videoUrl = this.videoUrl
    )
}