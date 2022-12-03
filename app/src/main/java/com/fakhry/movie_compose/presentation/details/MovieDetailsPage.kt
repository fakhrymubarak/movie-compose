package com.fakhry.movie_compose.presentation.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fakhry.movie_compose.core.factory.ViewModelFactory


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailsPage(
    movieId: Int,
    viewModel: MovieDetailsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateBack: () -> Unit,


    ) {
    val context = LocalContext.current
    val listMoviesState by viewModel.movieDetailsState.collectAsState()

}
