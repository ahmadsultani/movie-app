package com.D121211080.movieapp.data.response

import com.D121211080.movieapp.data.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMovieResponse(
    @SerialName("results") val results: List<Movie>?,
    @SerialName("page") val page: Int?,
    @SerialName("total_pages") val totalPages: Int?,
    @SerialName("total_results") val totalResults: Int?,
)