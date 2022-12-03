package com.fakhry.movie_compose.domain.model

import com.fakhry.movie_compose.core.utils.BASE_POSTER_URL
import com.fakhry.movie_compose.data.dummy.movie
import com.fakhry.movie_compose.data.dummy.movieEntity
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class MovieTest {

    @Test
    fun `movie toMovieEntity successReturnMovieEntity`() {
        val result = movie.toMovieEntity()

        assertThat(result.movieId).isEqualTo(movieEntity.movieId)
        assertThat(result.posterPath).doesNotContain(BASE_POSTER_URL)
    }
}