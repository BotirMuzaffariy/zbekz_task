package uz.lazydevv.zbekztask.presentation.ui.singlelesson

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
class SingleLessonViewModel @Inject constructor(
    private val lessonsRepo: LessonsRepo,
    private val sharedPrefs: AppSharedPrefs
) : ViewModel() {

    private var _lessons = MutableStateFlow<Resource<List<LessonM>>>(Resource.Loading())
    val lessons = _lessons.asStateFlow()

    private var _purchasedState = MutableStateFlow(false)
    val purchasedState = _purchasedState.asStateFlow()

    fun getLessons() {
        lessonsRepo.getLocalLessons()
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

    fun checkPurchasedState() {
        _purchasedState.value = sharedPrefs.isPaid
    }

    fun onPurchaseClicked() {
        if (!sharedPrefs.isPaid) {
            sharedPrefs.isPaid = true
            checkPurchasedState()

            lessonsRepo.fetchLessons()
                .flowOn(Dispatchers.IO)
                .launchIn(viewModelScope)
        }
    }
}