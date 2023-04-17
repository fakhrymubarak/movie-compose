package com.fakhry.movie_compose.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakhry.movie_compose.core.utils.UiState
import com.fakhry.movie_compose.data.Resource
import com.fakhry.movie_compose.domain.model.MovieDetails
import com.fakhry.movie_compose.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movieDetailsState =
        MutableStateFlow<UiState<MovieDetails>>(UiState.Initial)
    val movieDetailsState = _movieDetailsState.asStateFlow()

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId).collect { resource ->
                when (resource) {
                    Resource.Loading -> _movieDetailsState.emit(UiState.Loading(true))
                    is Resource.Success -> {
                        _movieDetailsState.emit(UiState.Loading(false))
                        _movieDetailsState.emit(UiState.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _movieDetailsState.emit(UiState.Loading(false))
                        _movieDetailsState.emit(UiState.Error(resource.uiText))
                    }
                }
            }
        }
    }
}