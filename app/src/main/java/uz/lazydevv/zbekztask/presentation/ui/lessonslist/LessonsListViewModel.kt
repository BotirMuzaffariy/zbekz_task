package uz.lazydevv.zbekztask.presentation.ui.lessonslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import uz.lazydevv.zbekztask.data.models.LessonM
import uz.lazydevv.zbekztask.data.preferences.AppSharedPrefs
import uz.lazydevv.zbekztask.domain.repositories.LessonsRepo
import uz.lazydevv.zbekztask.presentation.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LessonsListViewModel @Inject constructor(
    private val lessonsRepo: LessonsRepo,
    private val sharedPrefs: AppSharedPrefs
) : ViewModel() {

    private var _lessons = MutableStateFlow<Resource<List<LessonM>>>(Resource.Loading())
    val lessons = _lessons.asStateFlow()

    fun fetchLessons() {
        lessonsRepo.fetchLessons()
            .onStart {
                _lessons.value = Resource.Loading()
            }
            .onEach {
                if (it.isNotEmpty()) {
                    _lessons.value = Resource.Success(it)
                } else {
                    _lessons.value = Resource.Empty()
                }
            }
            .catch {
                _lessons.value = Resource.Error(it.message)
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun onPurchaseClicked() {
        if (!sharedPrefs.isPaid) {
            sharedPrefs.isPaid = true
            fetchLessons()
        }
    }
}