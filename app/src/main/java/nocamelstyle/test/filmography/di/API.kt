package nocamelstyle.test.filmography.di

import nocamelstyle.test.filmography.models.Film
import nocamelstyle.test.filmography.models.Wrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {

    @GET("movie/{id}")
    suspend fun getFilmInfo(
        @Path("id") id: Int
    ): Film

    @GET("movie/popular")
    suspend fun getPopularFilms(
        @Query("language") language: String,
        @Query("page") page: Int
    ): Wrapper

    @GET("search/movie")
    suspend fun getPopularFilmsSearch(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("query") name: String
    ): Wrapper

}