package com.fakhry.movie_compose.core.utils

sealed class UiState<out T> {
    object Initial : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Loading(val isLoading: Boolean) : UiState<Nothing>()
    data class Error(val uiText: UiText) : UiState<Nothing>()
}