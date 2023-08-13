package uz.lazydevv.zbekztask.presentation.ui.lessonslist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import uz.lazydevv.zbekztask.data.models.LessonM
import uz.lazydevv.zbekztask.databinding.ItemLessonBinding
import uz.lazydevv.zbekztask.presentation.extensions.loadImageUrl

class RvLessonsAdapter(
    val onLessonClicked: (position: Int) -> Unit,
    val onClosedLessonClicked: () -> Unit,
) : RecyclerView.Adapter<RvLessonsAdapter.LessonVh>() {

    private val diffUtilCallback = object : DiffUtil.ItemCallback<LessonM>() {
        override fun areItemsTheSame(oldItem: LessonM, newItem: LessonM): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LessonM, newItem: LessonM): Boolean {
            return oldItem.id == newItem.id
        }
    }

    private val diffUtil = AsyncListDiffer(this, diffUtilCallback)

    inner class LessonVh(private val itemBinding: ItemLessonBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun onBind(lesson: LessonM) {
            with(itemBinding) {
                root.alpha = if (lesson.isOpen) 1f else 0.5f

                tvTitle.text = lesson.title
                tvDescription.text = lesson.description

                ivThumbnail.loadImageUrl(lesson.thumbnailUrl)

                root.setOnClickListener {
                    if (lesson.isOpen) {
                        onLessonClicked.invoke(absoluteAdapterPosition)
                    } else {
                        onClosedLessonClicked.invoke()
                    }
                }
            }
        }
    }

    fun submitLessons(newLessons: List<LessonM>?) {
        diffUtil.submitList(newLessons)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonVh {
        return LessonVh(ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = diffUtil.currentList.size

    override fun onBindViewHolder(holder: LessonVh, position: Int) {
        holder.onBind(diffUtil.currentList[position])
    }
}