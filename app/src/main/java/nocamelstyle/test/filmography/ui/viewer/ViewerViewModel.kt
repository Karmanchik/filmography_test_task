package nocamelstyle.test.filmography.ui.viewer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import nocamelstyle.test.filmography.core.Status
import nocamelstyle.test.filmography.core.ViewState
import nocamelstyle.test.filmography.core.convertToFlowViewState
import nocamelstyle.test.filmography.models.Film
import nocamelstyle.test.filmography.services.FilmsUseCaseFlow
import javax.inject.Inject

class ViewerViewModel @Inject constructor(
    private val listFilmsUseCaseFlow: FilmsUseCaseFlow
) : ViewModel() {

    val state = MutableLiveData<ViewState<Film>>()

    fun getFilmInfo(id: Int) {
        listFilmsUseCaseFlow.getFilmDetailInfo(id).convertToFlowViewState()
            .onStart { state.value = ViewState(Status.LOADING) }
            .onEach { state.value = it }
            .catch { state.value = ViewState(Status.ERROR, error = it) }
            .launchIn(viewModelScope)
    }

}