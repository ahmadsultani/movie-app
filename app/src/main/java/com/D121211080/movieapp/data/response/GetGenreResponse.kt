package com.D121211080.movieapp.data.response

import com.D121211080.movieapp.data.model.Genre
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetGenreResponse(
    @SerialName("genres") val genres: List<Genre>,
)