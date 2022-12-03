package com.fakhry.movie_compose.data.database.entity

import com.fakhry.movie_compose.core.utils.BASE_BACKDROP_URL
import com.fakhry.movie_compose.core.utils.BASE_POSTER_URL
import com.fakhry.movie_compose.data.dummy.movie
import com.fakhry.movie_compose.data.dummy.movieDetails
import com.fakhry.movie_compose.data.dummy.movieEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MovieEntityTest {
    @Test
    fun `movieEntity toMovie successReturnMovie`() {
        val result = movieEntity.toMovie()

        assertThat(result).isEqualTo(movie)
        assertThat(result.posterPath).contains(BASE_POSTER_URL)
    }

    @Test
    fun `movieEntity toMovieDetails successReturnMovieDetails`() {
        val result = movieEntity.toMovieDetails()

        assertThat(result).isEqualTo(movieDetails)
        assertThat(result.posterPath).contains(BASE_POSTER_URL)
        assertThat(result.backdropPath).contains(BASE_BACKDROP_URL)
    }
}