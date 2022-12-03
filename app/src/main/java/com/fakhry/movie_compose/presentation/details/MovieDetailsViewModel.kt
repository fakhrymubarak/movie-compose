package com.fakhry.movie_compose.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakhry.movie_compose.core.utils.UiStateWrapper
import com.fakhry.movie_compose.data.Resource
import com.fakhry.movie_compose.domain.model.MovieDetails
import com.fakhry.movie_compose.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movieDetailsState =
        MutableStateFlow<UiStateWrapper<MovieDetails>>(UiStateWrapper.Initial)
    val movieDetailsState = _movieDetailsState.asStateFlow()

    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId).collect { resource ->
                when (resource) {
                    Resource.Loading -> _movieDetailsState.emit(UiStateWrapper.Loading(true))
                    is Resource.Success -> {
                        _movieDetailsState.emit(UiStateWrapper.Loading(false))
                        _movieDetailsState.emit(UiStateWrapper.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _movieDetailsState.emit(UiStateWrapper.Loading(false))
                        _movieDetailsState.emit(UiStateWrapper.Error(resource.uiText))
                    }
                }
            }
        }
    }
}