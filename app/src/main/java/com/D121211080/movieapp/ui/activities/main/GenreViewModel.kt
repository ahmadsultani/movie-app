import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.D121211080.movieapp.MovieApp
import com.D121211080.movieapp.data.model.Genre
import com.D121211080.movieapp.data.repository.GenreRepository
import com.D121211080.movieapp.ui.activities.main.MovieViewModel
import kotlinx.coroutines.launch

sealed interface GenreUiState {
    data class Success(val genres: List<Genre>) : GenreUiState
    data object Error : GenreUiState
    data object Loading : GenreUiState
}

class GenreViewModel(private val genreRepository: GenreRepository) : ViewModel() {
    var genreUiState: GenreUiState by mutableStateOf(GenreUiState.Loading)
        private set

    fun getAllGenre() = viewModelScope.launch {
        genreUiState = GenreUiState.Loading
        try {
            val result = genreRepository.getAllGenre()
            genreUiState = GenreUiState.Success(result.genres)
        } catch (e: Exception) {
            genreUiState = GenreUiState.Error
            Log.d("Genre View Model", "getAllGenre Error")
        }
    }

    init {
        getAllGenre()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MovieApp)
                val genreRepository = application.container.genreRepository
                GenreViewModel(genreRepository)
            }
        }
    }
}