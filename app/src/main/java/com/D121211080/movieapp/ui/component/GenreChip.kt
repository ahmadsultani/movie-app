package com.D121211080.movieapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.D121211080.movieapp.data.model.Genre

@Composable
fun GenreChip(genre: Genre, selected: Boolean, onGenreSelected: (Genre) -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onGenreSelected(genre) },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer else Color.Gray
        ),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(
                text = genre.name,
                color = if (selected) Color.White else Color.Black,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}