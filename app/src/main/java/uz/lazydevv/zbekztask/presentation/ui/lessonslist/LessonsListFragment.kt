package uz.lazydevv.zbekztask.presentation.ui.lessonslist

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import uz.lazydevv.zbekztask.R
import uz.lazydevv.zbekztask.databinding.BottomSheetPurchaseBinding
import uz.lazydevv.zbekztask.databinding.FragmentLessonsListBinding
import uz.lazydevv.zbekztask.presentation.extensions.collectLatestOnStarted
import uz.lazydevv.zbekztask.presentation.extensions.navigate
import uz.lazydevv.zbekztask.presentation.extensions.showToast
import uz.lazydevv.zbekztask.presentation.ui.lessonslist.adapters.RvLessonsAdapter
import uz.lazydevv.zbekztask.presentation.utils.Resource

@AndroidEntryPoint
class LessonsListFragment : Fragment(R.layout.fragment_lessons_list) {

    private val viewModel by viewModels<LessonsListViewModel>()

    private val binding by viewBinding(FragmentLessonsListBinding::bind)

    private val purchaseBinding by lazy {
        BottomSheetPurchaseBinding.inflate(layoutInflater)
    }

    private val purchaseSheet by lazy {
        BottomSheetDialog(requireContext()).apply {
            setContentView(purchaseBinding.root)
        }
    }

    private val lessonsAdapter by lazy {
        RvLessonsAdapter(
            onLessonClicked = { position ->
                val bundle = bundleOf(KEY_LESSON_POS to position)
                navigate(R.id.action_lessonsListFragment_to_singleLessonFragment, bundle)
            },
            onClosedLessonClicked = {
                openPurchaseSheet()
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchLessons()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLessons()

        binding.rvLessons.adapter = lessonsAdapter

        purchaseBinding.btnPurchase.setOnClickListener {
            viewModel.onPurchaseClicked()
            purchaseSheet.dismiss()
            showToast(getString(R.string.all_lessons_opened))
        }
    }

    private fun observeLessons() {
        collectLatestOnStarted(viewModel.lessons) {
            with(binding) {
                rvLessons.isVisible = it !is Resource.Loading
                pbLoading.isVisible = it is Resource.Loading
                tvError.isVisible = it is Resource.Error

                when (it) {
                    is Resource.Error -> tvError.text = it.message

                    is Resource.Success -> lessonsAdapter.submitLessons(it.data)

                    else -> Unit
                }
            }
        }
    }

    private fun openPurchaseSheet() {
        purchaseSheet.show()
    }

    companion object {

        const val KEY_LESSON_POS = "lesson_pos"
    }
}