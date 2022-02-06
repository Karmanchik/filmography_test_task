package nocamelstyle.test.filmography.services

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import nocamelstyle.test.filmography.di.API
import nocamelstyle.test.filmography.models.Film
import javax.inject.Inject

class FilmsUseCaseFlow @Inject constructor(
    private val api: API
) {

    fun getFilmDetailInfo(idFilm: Int): Flow<Film> {
        return flow {
            emit(getFilm(idFilm))
        }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getListFilms(language: String, page: Int): List<Film> =
        api.getPopularFilms(language = language, page = page).results

    suspend fun getListFilmsSearch(language: String, page: Int, name: String): List<Film> =
        api.getPopularFilmsSearch(language = language, page = page, name = name).results

    private suspend fun getFilm(id: Int): Film =
        api.getFilmInfo(id = id)

}