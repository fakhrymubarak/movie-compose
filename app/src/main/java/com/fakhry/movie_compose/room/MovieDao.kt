package com.fakhry.movie_compose.room

import androidx.room.*
import com.fakhry.movie_compose.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert
    fun insertListMovies(listMovie: List<MovieEntity>)

    @Query("SELECT * FROM movie_entities ORDER BY id_movie ASC")
    fun getListMovies(): List<MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE id_movie = :movieId")
    fun getMovieDetails(movieId: Int): MovieEntity?

    @Query("DELETE FROM movie_entities WHERE id_movie = :movieId")
    fun deleteMovie(movieId: Int)
}