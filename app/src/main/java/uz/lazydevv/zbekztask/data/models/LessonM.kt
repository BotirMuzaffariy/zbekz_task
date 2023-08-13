package uz.lazydevv.zbekztask.data.models

import java.io.Serializable

data class LessonM(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val videoUrl: String,
) : Serializable
