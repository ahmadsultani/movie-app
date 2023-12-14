package com.D121211080.movieapp.data.repository

import com.D121211080.movieapp.data.response.GetGenreResponse
import com.D121211080.movieapp.data.source.remote.ApiService

class GenreRepository(private val apiService: ApiService) {
    suspend fun getAllGenre(): GetGenreResponse {
        return apiService.getAllGenre()
    }
}