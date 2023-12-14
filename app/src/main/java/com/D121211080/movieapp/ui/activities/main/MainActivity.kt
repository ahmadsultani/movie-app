package com.D121211080.movieapp.ui.activities.main

import GenreViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.D121211080.movieapp.R
import com.D121211080.movieapp.data.model.Genre
import com.D121211080.movieapp.data.model.Movie
import com.D121211080.movieapp.ui.activities.detail.DetailActivity
import com.D121211080.movieapp.ui.component.CoilImage
import com.D121211080.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val mainViewModel: MovieViewModel = viewModel(factory = MovieViewModel.Factory)
                    val genreViewModel: GenreViewModel = viewModel(factory = GenreViewModel.Factory)
                    var selectedGenre by remember { mutableStateOf<Genre?>(null) }
                    val coroutineScope = rememberCoroutineScope()


                    Column {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                text = "Movie List",
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.headlineLarge,
                            )
                        },
                            actions = {
                                IconButton(onClick = {
                                    mainViewModel.refreshMovies()
                                    genreViewModel.getAllGenre()
                                    selectedGenre = null
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "Refresh"
                                    )
                                }
                            },
                            modifier = Modifier.background(Color.Blue)  // Change background color here
                        )

                        GenreChips(
                            genreViewModel.genreUiState, selectedGenre
                        ) { genre ->
                            selectedGenre = if (selectedGenre === genre) null else genre
                            coroutineScope.launch {
                                mainViewModel.getAllMovie(
                                    1, if (selectedGenre !== null) "${genre.id}" else ""
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        MainScreen(mainViewModel.mainUiState, {
                            mainViewModel.refreshMovies(if (selectedGenre !== null) "${selectedGenre!!.id}" else "")
                        })

                    }


                }
            }
        }
    }

    @Composable
    fun MainScreen(
        mainUiState: MainUiState, onRefresh: () -> Unit, modifier: Modifier = Modifier
    ) {
        when (mainUiState) {
            is MainUiState.Success -> {
                val movies = mainUiState.result.results.orEmpty()
                LazyColumn(modifier) {
                    items(movies) { movie ->
                        MovieListItem(movie)
                    }
                }
            }

            is MainUiState.Error -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = "Error Icon",
                        modifier = Modifier.size(120.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Error loading movies",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onRefresh, colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ), shape = MaterialTheme.shapes.medium
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Refresh")
                    }
                }
            }

            is MainUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    )
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MovieListItem(movie: Movie) {
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            shape = RoundedCornerShape(0),
            colors = CardDefaults.cardColors(
                containerColor = Color.DarkGray
            ),
            onClick = {
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("movie", movie)
                startActivity(intent)
            }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(120.dp)
            ) {
                // Movie Poster
                CoilImage(
                    data = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                    contentDescription = movie.title,
                    modifier = Modifier.fillMaxHeight()
                )

                // Spacer
                Spacer(modifier = Modifier.width(16.dp))

                // Movie Details
                Column {
                    Text(
                        text = movie.title ?: "" ,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = movie.releaseDate ?: "",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movie.overview ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


