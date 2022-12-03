package com.fakhry.movie_compose.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val releaseYear: String,
    val overview: String,
    val posterPath: String,
    val rating: Double,
)