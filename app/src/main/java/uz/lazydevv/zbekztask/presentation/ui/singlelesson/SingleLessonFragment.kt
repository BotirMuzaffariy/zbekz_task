package uz.lazydevv.zbekztask.presentation.ui.singlelesson

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import uz.lazydevv.zbekztask.R
import uz.lazydevv.zbekztask.data.models.LessonM
import uz.lazydevv.zbekztask.databinding.BottomSheetPurchaseBinding
import uz.lazydevv.zbekztask.databinding.FragmentSingleLessonBinding
import uz.lazydevv.zbekztask.presentation.extensions.collectLatestOnStarted
import uz.lazydevv.zbekztask.presentation.ui.App
import uz.lazydevv.zbekztask.presentation.ui.lessonslist.LessonsListFragment
import uz.lazydevv.zbekztask.presentation.utils.AppConstants.DEFAULT_OPENED_LESSONS_COUNT
import uz.lazydevv.zbekztask.presentation.utils.Resource

@AndroidEntryPoint
class SingleLessonFragment : Fragment(R.layout.fragment_single_lesson) {

    private val viewModel by viewModels<SingleLessonViewModel>()

    private val binding by viewBinding(FragmentSingleLessonBinding::bind)

    private var lessons: List<LessonM> = emptyList()

    private var currentLessonPos = 0
    private var isPurchased = false

    private lateinit var exoPlayer: ExoPlayer

    private val purchaseBinding by lazy {
        BottomSheetPurchaseBinding.inflate(layoutInflater)
    }

    private val purchaseSheet by lazy {
        BottomSheetDialog(requireContext()).apply {
            setContentView(purchaseBinding.root)
        }
    }
    private val exoPlayerListener by lazy {
        object : Player.Listener {
            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)

                if (!isPurchased) {
                    // check for next btn click
                    if (currentLessonPos + 1 == exoPlayer.currentMediaItemIndex) {
                        if (exoPlayer.currentMediaItemIndex >= DEFAULT_OPENED_LESSONS_COUNT) {
                            purchaseSheet.show()

                            exoPlayer.seekToPreviousMediaItem()
                            exoPlayer.pause()

                            return
                        }
                    }
                }

                currentLessonPos = exoPlayer.currentMediaItemIndex
                setupLessonData(lessons[currentLessonPos])
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentLessonPos = arguments?.getInt(LessonsListFragment.KEY_LESSON_POS, 0) ?: 0

        viewModel.getLessons()
        viewModel.checkPurchasedState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLessons()

        observePurchasedState()

        with(binding) {
            playerView.controllerAutoShow = false

            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }

        purchaseBinding.btnPurchase.setOnClickListener {
            viewModel.onPurchaseClicked()
            purchaseSheet.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        exoPlayer.playWhenReady = true
    }

    override fun onPause() {
        exoPlayer.playWhenReady = false
        super.onPause()
    }

    override fun onDestroyView() {
        releasePlayer()
        super.onDestroyView()
    }

    private fun setupLessonData(lesson: LessonM?) {
        if (lesson == null) return

        with(binding) {
            toolbar.subtitle = lesson.title

            tvTitle.text = lesson.title
            tvDescription.text = lesson.description
        }
    }

    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(requireContext()).build()

        val cacheDataSourceFactory = CacheDataSource.Factory()
            .setCache(App.exoPlayerCache)
            .setUpstreamDataSourceFactory(DefaultDataSource.Factory(requireContext()))

        val mediaSources = lessons.map {
            ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(MediaItem.fromUri(it.videoUrl))
        }

        exoPlayer.setMediaSources(mediaSources)
        exoPlayer.seekTo(currentLessonPos, 0L)
        exoPlayer.prepare()

        exoPlayer.addListener(exoPlayerListener)

        binding.playerView.player = exoPlayer
    }

    private fun releasePlayer() {
        exoPlayer.removeListener(exoPlayerListener)
        exoPlayer.release()
    }

    private fun observeLessons() {
        collectLatestOnStarted(viewModel.lessons) {
            when (it) {
                is Resource.Success -> {
                    lessons = it.data ?: emptyList()

                    initializePlayer()
                    setupLessonData(lessons[currentLessonPos])
                }

                else -> Unit
            }
        }
    }

    private fun observePurchasedState() {
        collectLatestOnStarted(viewModel.purchasedState) {
            isPurchased = it
        }
    }
}