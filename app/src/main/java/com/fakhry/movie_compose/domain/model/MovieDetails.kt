package com.fakhry.movie_compose.domain.model

data class MovieDetails(
    var id: Int,
    var title: String,
    var releaseYear: String,
    var overview: String,
    var posterPath: String,
    var backdropPath: String,
    var voteAverage: Double
)
