package com.fakhry.movie_compose.domain.repository

import com.fakhry.movie_compose.data.Resource
import com.fakhry.movie_compose.domain.model.Movie
import com.fakhry.movie_compose.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getListMovie(): Flow<Resource<List<Movie>>>
    fun queryMovie(query: String): Flow<Resource<List<Movie>>>
    fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>
    fun deleteMovie(movie: Movie): Flow<Resource<Boolean>>
}