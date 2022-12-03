package com.fakhry.movie_compose.presentation

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.fakhry.movie_compose.R
import com.fakhry.movie_compose.common.theme.MovieComposeTheme
import com.fakhry.movie_compose.common.values.radiusRegular
import com.fakhry.movie_compose.common.values.spacingRegular
import com.fakhry.movie_compose.common.values.spacingSmaller
import com.fakhry.movie_compose.core.factory.ViewModelFactory
import com.fakhry.movie_compose.core.utils.UiStateWrapper
import com.fakhry.movie_compose.core.utils.asString
import com.fakhry.movie_compose.di.Injection
import com.fakhry.movie_compose.domain.model.Movie
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListMovieApp(
    modifier: Modifier = Modifier,
    viewModel: ListMovieViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository(LocalContext.current))
    )
) {
    val context = LocalContext.current
    val listMoviesState by viewModel.listMovieState.collectAsState()

    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            when (val state: UiStateWrapper<List<Movie>> = listMoviesState) {
                UiStateWrapper.Initial -> viewModel.fetchListMovie()
                is UiStateWrapper.Loading -> {}
                is UiStateWrapper.Success -> {
                    val movies = state.data

                    items(movies, key = { it.id }) { movie ->
                        MovieItem(
                            title = movie.title,
                            overview = movie.overview,
                            posterUrl = movie.posterPath,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(tween(durationMillis = 100))
                        )
                    }
                }
                is UiStateWrapper.Error -> Toast.makeText(
                    context,
                    state.uiText.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Composable
fun MovieItem(
    title: String,
    overview: String,
    posterUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .clickable {}
            .height(IntrinsicSize.Min)
            .padding(PaddingValues(vertical = spacingSmaller))

    ) {
        AsyncImage(
            model = posterUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(125.dp)
                .padding(PaddingValues(horizontal = spacingSmaller))
                .width(75.dp)
                .clip(RoundedCornerShape(radiusRegular))
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(PaddingValues(horizontal = spacingSmaller))

        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            )
            Text(
                text = overview,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            contentColor = MaterialTheme.colors.primary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}


@Preview(showBackground = true)
@Composable
fun HeroListItemPreview() {
    MovieComposeTheme {
        MovieItem(
            title = "Hello World",
            posterUrl = "",
            overview = "Lorem Lorem Lorem Ipsum Lorem Lorem Lorem Ipsum Lorem Lorem Lorem Ipsum Lorem Lorem Lorem Ipsum Lorem Lorem Lorem Ipsum Lorem Lorem Lorem Ipsum"
        )
    }
}