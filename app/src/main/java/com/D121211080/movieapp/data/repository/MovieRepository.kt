package com.D121211080.movieapp.data.repository

import com.D121211080.movieapp.data.response.GetMovieResponse
import com.D121211080.movieapp.data.source.remote.ApiService

class MovieRepository(private val apiService: ApiService) {
    suspend fun getAllMovie(page: Int, genres: String): GetMovieResponse {
        return apiService.getAllMovie(page, genres)
    }
}