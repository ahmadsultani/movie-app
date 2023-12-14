package com.D121211080.movieapp.data

import com.D121211080.movieapp.data.repository.GenreRepository
import com.D121211080.movieapp.data.repository.MovieRepository
import com.D121211080.movieapp.data.source.remote.ApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val movieRepository: MovieRepository
    val genreRepository: GenreRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://api.themoviedb.org"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val movieRepository: MovieRepository
        get() = MovieRepository(retrofitService)

    override val genreRepository: GenreRepository
        get() = GenreRepository(retrofitService)

}