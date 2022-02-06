package nocamelstyle.test.filmography.services

import androidx.paging.PagingSource
import androidx.paging.PagingState
import nocamelstyle.test.filmography.models.Film

class FilmsDataSource(
    private val useCase: FilmsUseCaseFlow,
    private val language: String,
    private val search: String
) : PagingSource<Int, Film>() {

    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: 1
        val pageSize = params.loadSize.coerceAtMost(20)

        return try {

            val response =
                if (search.isEmpty()) {
                    useCase.getListFilms(language, page)
                } else {
                    useCase.getListFilmsSearch(language, page, search)
                }

            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}