package com.fakhry.movie_compose.domain.model

import com.fakhry.movie_compose.core.utils.BASE_POSTER_URL
import com.fakhry.movie_compose.data.database.entity.MovieEntity

data class Movie(
    val id: Int,
    val title: String,
    val releaseYear: String,
    val overview: String,
    val posterPath: String,
    val rating: Double,
) {
    fun toMovieEntity(): MovieEntity = MovieEntity(
        movieId = id,
        title = title,
        releaseDate = "",
        overview = overview,
        posterPath = posterPath.removePrefix(BASE_POSTER_URL),
        backdropPath = "",
        voteAverage = rating,
    )
}
