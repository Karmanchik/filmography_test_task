package nocamelstyle.test.filmography.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import nocamelstyle.test.filmography.services.FilmsDataSource
import nocamelstyle.test.filmography.services.FilmsUseCaseFlow
import javax.inject.Inject

class FilmsViewModel @Inject constructor(
    private val filmsUseCaseFlow: FilmsUseCaseFlow
) : ViewModel() {

    var search = ""

    val filmsSource = Pager(PagingConfig(pageSize = 20)) {
        FilmsDataSource(filmsUseCaseFlow, "en-US", search)
    }
        .flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
        .asLiveData(Dispatchers.Main)

}