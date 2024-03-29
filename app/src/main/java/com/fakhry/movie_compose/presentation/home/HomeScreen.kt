package com.fakhry.movie_compose.presentation.home

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.fakhry.movie_compose.R
import com.fakhry.movie_compose.common.theme.SansSerif
import com.fakhry.movie_compose.common.values.radiusRegular
import com.fakhry.movie_compose.common.values.spacingRegular
import com.fakhry.movie_compose.common.values.spacingSmaller
import com.fakhry.movie_compose.core.factory.ViewModelFactory
import com.fakhry.movie_compose.core.utils.UiState
import com.fakhry.movie_compose.core.utils.asString
import com.fakhry.movie_compose.domain.model.Movie
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (Int) -> Unit,
    navigateToAbout: () -> Unit,
) {
    val context = LocalContext.current
    val listMovieState = viewModel.listMovieState.collectAsState()

    Scaffold(
        topBar = { AppBarHome(navigateToAbout = { navigateToAbout() }) },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            val query by viewModel.query

            Box(modifier = modifier) {
                val scope = rememberCoroutineScope()
                val listState = rememberLazyListState()
                val showButton: Boolean by remember {
                    derivedStateOf { listState.firstVisibleItemIndex > 0 }
                }
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = modifier
                ) {
                    SearchBar(
                        query = query,
                        onQueryChange = viewModel::queryMovies,
                        modifier = Modifier.background(MaterialTheme.colors.primary)
                    )
                    Box(modifier = modifier.fillMaxHeight()) {
                        listMovieState.value.let { state ->
                            when (state) {
                                UiState.Initial -> viewModel.fetchListMovie()
                                is UiState.Loading -> CircularProgressIndicator()
                                is UiState.Empty -> Text(
                                    text = "List Movie is Empty",
                                    textAlign = TextAlign.Center,
                                    style = SansSerif.Sp14.Regular,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(top = spacingRegular)
                                )
                                is UiState.Success -> {
                                    HomeScreenContent(
                                        listState = listState,
                                        movies = state.data,
                                        navigateToDetail = navigateToDetail
                                    )
                                }
                                is UiState.Error -> Toast.makeText(
                                    context,
                                    state.uiText.asString(context),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
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
    }
}

@Composable
fun AppBarHome(navigateToAbout: () -> Unit) {
    TopAppBar(
        actions = {
            IconButton(onClick = {
                navigateToAbout()
            }) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = stringResource(R.string.about_page)
                )
            }
        },
        title = {
            Text(stringResource(R.string.app_name))
        },
    )
}


@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(stringResource(R.string.search_movie))
        },
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreenContent(
    listState: LazyListState,
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieItem(
                movie = movie,
                modifier = modifier
                    .fillMaxWidth()
                    .animateItemPlacement(tween(durationMillis = 100)),
                navigateToDetail = navigateToDetail
            )
        }
    }
}


@Composable
fun MovieItem(
    movie: Movie,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .clickable { navigateToDetail(movie.id) }
            .height(IntrinsicSize.Min)
            .padding(PaddingValues(vertical = spacingSmaller))

    ) {
        AsyncImage(
            model = movie.posterPath,
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
                text = movie.title,
                style = SansSerif.Sp16.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            )
            Text(
                text = movie.overview,
                style = SansSerif.Sp14.Regular,
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