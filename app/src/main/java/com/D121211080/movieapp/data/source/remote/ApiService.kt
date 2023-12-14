package com.D121211080.movieapp.data.source.remote

import com.D121211080.movieapp.data.response.GetGenreResponse
import com.D121211080.movieapp.data.response.GetMovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwYmIwMGYwYjNlZDJjM2VkN2IyZjgzZDJkMjRjZjc0NyIsInN1YiI6IjYzZTcxYzg5NjNhYWQyMDBhMTRiY2I4MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xlk6CgIlX2k0Cj1y_8KanY9rrxkWjM07GgJlcPYe3cs")
    @GET("3/discover/movie")
    suspend fun getAllMovie(
        @Query("page") page: Int = 1,
        @Query("with_genres") genres: String,
        @Query("language") language: String = "en-US",
    ): GetMovieResponse

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwYmIwMGYwYjNlZDJjM2VkN2IyZjgzZDJkMjRjZjc0NyIsInN1YiI6IjYzZTcxYzg5NjNhYWQyMDBhMTRiY2I4MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.xlk6CgIlX2k0Cj1y_8KanY9rrxkWjM07GgJlcPYe3cs")
    @GET("3/genre/movie/list")
    suspend fun getAllGenre(
    ): GetGenreResponse


}