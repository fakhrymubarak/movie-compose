package com.fakhry.movie_compose.data.dummy

import com.fakhry.movie_compose.core.utils.BASE_BACKDROP_URL
import com.fakhry.movie_compose.core.utils.BASE_POSTER_URL
import com.fakhry.movie_compose.data.database.entity.MovieEntity
import com.fakhry.movie_compose.domain.model.Movie
import com.fakhry.movie_compose.domain.model.MovieDetails


val movieEntity = MovieEntity(
    movieId = 436270,
    title = "Black Adam",
    releaseDate = "2022-10-19",
    overview = "overview",
    posterPath = "/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg",
    backdropPath = "/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg",
    voteAverage = 7.3,
)

val movie = Movie(
    id = 436270,
    title = "Black Adam",
    releaseYear = "2022",
    overview = "overview",
    posterPath = "$BASE_POSTER_URL/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg",
    rating = 7.3,
)

val movieDetails = MovieDetails(
    id = 436270,
    title = "Black Adam",
    releaseYear = "2022",
    overview = "overview",
    posterPath = "$BASE_POSTER_URL/pFlaoHTZeyNkG83vxsAJiGzfSsa.jpg",
    backdropPath = "$BASE_BACKDROP_URL/bQXAqRx2Fgc46uCVWgoPz5L5Dtr.jpg",
    voteAverage = 7.3,
)

val listMovie = listOf(movie)