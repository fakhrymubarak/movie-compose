package com.fakhry.movie_compose.data

import com.fakhry.movie_compose.core.utils.UiText

sealed class Resource<out R> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val uiText: UiText) : Resource<Nothing>()
}