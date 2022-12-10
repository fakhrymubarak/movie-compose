package com.fakhry.movie_compose.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fakhry.movie_compose.core.utils.UiStateWrapper
import com.fakhry.movie_compose.data.Resource
import com.fakhry.movie_compose.domain.model.Movie
import com.fakhry.movie_compose.domain.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _listMovieState =
        MutableStateFlow<UiStateWrapper<List<Movie>>>(UiStateWrapper.Initial)
    val listMovieState = _listMovieState.asStateFlow()

    fun fetchListMovie() {
        viewModelScope.launch {
            repository.getListMovie().collect { resource ->
                when (resource) {
                    Resource.Loading -> _listMovieState.emit(UiStateWrapper.Loading(true))
                    is Resource.Success -> {
                        _listMovieState.emit(UiStateWrapper.Loading(false))
                        _listMovieState.emit(UiStateWrapper.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _listMovieState.emit(UiStateWrapper.Loading(false))
                        _listMovieState.emit(UiStateWrapper.Error(resource.uiText))
                    }
                }
            }
        }
    }

    fun queryMovies(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            repository.queryMovie(newQuery).collect { resource ->
                when (resource) {
                    Resource.Loading -> _listMovieState.emit(UiStateWrapper.Loading(true))
                    is Resource.Success -> {
                        _listMovieState.emit(UiStateWrapper.Loading(false))
                        _listMovieState.emit(UiStateWrapper.Success(resource.data))
                    }
                    is Resource.Error -> {
                        _listMovieState.emit(UiStateWrapper.Loading(false))
                        _listMovieState.emit(UiStateWrapper.Error(resource.uiText))
                    }
                }
            }
        }
    }
}