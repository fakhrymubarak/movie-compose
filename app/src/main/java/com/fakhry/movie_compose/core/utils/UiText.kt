package com.fakhry.movie_compose.core.utils

import android.content.Context
import androidx.annotation.StringRes
import com.fakhry.movie_compose.R

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    data class StringResource(@StringRes val id: Int) : UiText()
    data class StringResourceWithInt(@StringRes val id: Int, val params: Int) : UiText()

    companion object {
        fun unknownError(): UiText {
            return StringResource(R.string.text_error_unknown)
        }
    }
}

fun UiText.asString(context: Context) =
    when (this) {
        is UiText.DynamicString -> this.value
        is UiText.StringResource -> context.getString(this.id)
        is UiText.StringResourceWithInt -> context.getString(this.id, params)
    }
