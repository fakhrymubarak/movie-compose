package com.fakhry.movie_compose.core.utils

sealed class UiStateWrapper<out T> {
    object Initial : UiStateWrapper<Nothing>()
    object Empty : UiStateWrapper<Nothing>()
    data class Success<out T>(val data: T) : UiStateWrapper<T>()
    data class Loading(val isLoading: Boolean) : UiStateWrapper<Nothing>()
    data class Error(val uiText: UiText) : UiStateWrapper<Nothing>()
}