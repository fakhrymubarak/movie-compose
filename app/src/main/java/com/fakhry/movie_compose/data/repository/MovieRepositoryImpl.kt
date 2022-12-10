package com.fakhry.movie_compose.data.repository

import com.fakhry.movie_compose.core.utils.UiText
import com.fakhry.movie_compose.data.Resource
import com.fakhry.movie_compose.data.database.entity.MovieEntity
import com.fakhry.movie_compose.data.database.room.MovieDatabase
import com.fakhry.movie_compose.domain.model.Movie
import com.fakhry.movie_compose.domain.model.MovieDetails
import com.fakhry.movie_compose.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImpl(
    private val database: MovieDatabase
) : MovieRepository {
    override fun getListMovie(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        val listMovie = database.movieDao().getListMovies()
        try {
            if (listMovie.isEmpty()) {
                val dummyEntity = MovieEntity.generateDummyMovieEntity()
                database.movieDao().insertListMovies(dummyEntity)
                emit(Resource.Success(dummyEntity.map { it.toMovie() }))
                return@flow
            }
            emit(Resource.Success(listMovie.map { it.toMovie() }))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(UiText.DynamicString(e.message ?: "UnkownError")))
        }
    }.flowOn(Dispatchers.IO)

    override fun queryMovie(query: String): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading)
        val listMovie = database.movieDao().getListMovies(query = query)
        try {
            if (listMovie.isEmpty()) {
                emit(Resource.Success(listMovie.map { it.toMovie() }))
                return@flow
            }
            emit(Resource.Success(listMovie.map { it.toMovie() }))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(UiText.DynamicString(e.message ?: "UnkownError")))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> = flow {
        emit(Resource.Loading)
        val movie = database.movieDao().getMovieDetails(id)
        try {
            if (movie == null) {
                emit(Resource.Error(UiText.DynamicString("Movie Tidak Ditemukan")))
                return@flow
            }
            emit(Resource.Success(movie.toMovieDetails()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(UiText.DynamicString(e.message ?: "UnkownError")))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteMovie(movie: Movie): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
            database.movieDao().deleteMovie(movie.id)
            emit(Resource.Success(true))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error(UiText.DynamicString(e.message ?: "UnkownError")))
        }
    }.flowOn(Dispatchers.IO)


    companion object {
        private var INSTANCE: MovieRepositoryImpl? = null

        fun getInstance(movieDao: MovieDatabase): MovieRepositoryImpl {
            if (INSTANCE == null) {
                INSTANCE = MovieRepositoryImpl(movieDao)
            }
            return INSTANCE as MovieRepositoryImpl
        }
    }
}