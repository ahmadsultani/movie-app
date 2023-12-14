package com.D121211080.movieapp.ui.activities.main

import GenreViewModel
import com.D121211080.movieapp.MovieApp
import com.D121211080.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.launch
import java.io.IOException
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.D121211080.movieapp.data.model.Genre
import com.D121211080.movieapp.data.model.Movie
import com.D121211080.movieapp.data.response.GetMovieResponse

sealed interface MainUiState {
    data class Success(val result: GetMovieResponse) : MainUiState
    data object Error : MainUiState
    data object Loading : MainUiState
}

class MovieViewModel(
    private val movieRepository: MovieRepository,
) : ViewModel() {
    var mainUiState: MainUiState by mutableStateOf(MainUiState.Loading)
        private set

    var genres: List<Genre> by mutableStateOf(emptyList())
        private set

    fun getAllMovie(page: Int = 1, genres: String = "") = viewModelScope.launch {
        mainUiState = MainUiState.Loading
        try {
            val result = movieRepository.getAllMovie(page, genres)
            mainUiState = MainUiState.Success(result)
        } catch (e: Exception) {
            Log.d("Movie View Model", "Get Movies error: ${e.message}")
            mainUiState = MainUiState.Error
        }
    }

    fun refreshMovies(genres: String = "") {
        getAllMovie(1, genres)
    }

    init {
        getAllMovie()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApp)
                val movieRepository = application.container.movieRepository
                MovieViewModel(movieRepository)
            }
        }
    }
}