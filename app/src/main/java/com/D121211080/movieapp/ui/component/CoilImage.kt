package com.D121211080.movieapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter

@Composable
fun CoilImage(
    data: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    Image(
        painter = rememberAsyncImagePainter(data),
        contentDescription = contentDescription,
        modifier = modifier
    )
}
