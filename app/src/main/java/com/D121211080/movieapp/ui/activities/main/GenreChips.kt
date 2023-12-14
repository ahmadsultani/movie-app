package com.D121211080.movieapp.ui.activities.main

import GenreUiState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.D121211080.movieapp.data.model.Genre
import com.D121211080.movieapp.ui.component.GenreChip

@Composable
fun GenreChips(
    genreUiState: GenreUiState, selectedGenre: Genre?, onGenreSelected: (Genre) -> Unit
) {

    when (genreUiState) {
        is GenreUiState.Success -> {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(genreUiState.genres) { genre ->
                    GenreChip(genre,
                        selected = genre == selectedGenre,
                        onGenreSelected = { onGenreSelected(it) })
                }
            }
        }

        is GenreUiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error loading genres",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        is GenreUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
            }
        }

        else -> {}
    }
}