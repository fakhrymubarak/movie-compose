package com.fakhry.movie_compose.presentation.details

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.fakhry.movie_compose.R
import com.fakhry.movie_compose.common.theme.ColorWhiteTransparent50
import com.fakhry.movie_compose.common.theme.ColorYellowStar
import com.fakhry.movie_compose.common.theme.MovieComposeTheme
import com.fakhry.movie_compose.common.theme.SansSerif
import com.fakhry.movie_compose.common.values.radiusRegular
import com.fakhry.movie_compose.common.values.spacingRegular
import com.fakhry.movie_compose.common.values.spacingSmaller
import com.fakhry.movie_compose.core.factory.ViewModelFactory
import com.fakhry.movie_compose.core.utils.UiState
import com.fakhry.movie_compose.core.utils.asString
import com.fakhry.movie_compose.data.database.entity.MovieEntity
import com.fakhry.movie_compose.domain.model.MovieDetails
import com.fakhry.movie_compose.presentation.component.debugPlaceholder

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current
    val movieState = viewModel.movieDetailsState.collectAsState()
    movieState.value.let { state ->
        when (state) {
            UiState.Initial -> viewModel.fetchMovieDetails(movieId)
            is UiState.Loading -> {}
            is UiState.Empty -> {}
            is UiState.Success -> {
                LazyColumn {
                    val data = state.data
                    item {
                        MovieHeader(movie = data, onBackClick = navigateBack)
                        MovieBody(overview = data.overview)
                    }
                }
            }
            is UiState.Error -> Toast.makeText(
                context,
                state.uiText.asString(context),
                Toast.LENGTH_LONG
            ).show()
        }
    }

}

@Composable
fun MovieHeader(
    movie: MovieDetails,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Box(
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = movie.backdropPath,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                placeholder = debugPlaceholder(debugPreview = R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Companion.Transparent,
                                Color.Companion.White,
                            )
                        )
                    )
            )
        }
        Row(
            modifier = Modifier
                .padding(top = 150.dp, start = spacingRegular, end = spacingRegular)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = movie.posterPath,
                contentScale = ContentScale.Crop,
                placeholder = debugPlaceholder(debugPreview = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(radiusRegular))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues = PaddingValues(start = spacingSmaller))
            ) {
                Text(text = movie.title, style = SansSerif.Sp16.Bold)
                Text(text = movie.releaseYear, style = SansSerif.Sp14.Bold)
                Row {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = stringResource(R.string.star),
                        tint = ColorYellowStar,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = movie.voteAverage.toString(),
                        style = SansSerif.Sp14.Regular,
                    )
                }
            }
        }
        OutlinedButton(
            onClick = { onBackClick() },
            shape = CircleShape,
            border = BorderStroke(0.dp, Color.Transparent),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorWhiteTransparent50
            ),
            modifier = Modifier
                .padding(16.dp)
                .size(50.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
            )
        }
    }
}

@Composable
fun MovieBody(overview: String) {
    Text(
        text = overview,
        style = SansSerif.Sp14.Regular,
        modifier = Modifier.padding(
            PaddingValues(
                horizontal = spacingRegular,
                vertical = spacingRegular
            )
        )
    )
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DetailContentPreview() {
    MovieComposeTheme {
        MovieHeader(
            movie = MovieEntity.generateDummyMovieEntity().first().toMovieDetails(),
            onBackClick = {}
        )
    }
}
