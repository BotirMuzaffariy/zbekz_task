package uz.lazydevv.zbekztask.domain.mappers

import uz.lazydevv.zbekztask.data.db.entities.LessonEntity
import uz.lazydevv.zbekztask.data.models.LessonM
import uz.lazydevv.zbekztask.data.models.requests.LessonItem

fun List<LessonM>.toLessonEntityList(): List<LessonEntity> {
    return this.mapIndexed { index, lesson ->
        LessonEntity(
            id = index,
            lessonId = lesson.id,
            title = lesson.title,
            description = lesson.description,
            thumbnailUrl = lesson.thumbnailUrl,
            videoUrl = lesson.videoUrl,
            isOpen = lesson.isOpen
        )
    }
}

fun LessonEntity.toLessonM(): LessonM {
    return LessonM(
        id = this.lessonId,
        title = this.title,
        description = this.description,
        thumbnailUrl = this.thumbnailUrl,
        videoUrl = this.videoUrl,
        isOpen = this.isOpen
    )
}

fun LessonItem?.toLessonM(): LessonM {
    return LessonM(
        id = this?.id ?: 0,
        title = this?.name ?: "",
        description = this?.description ?: "",
        thumbnailUrl = this?.thumbnail ?: "",
        videoUrl = this?.video_url ?: ""
    )
}