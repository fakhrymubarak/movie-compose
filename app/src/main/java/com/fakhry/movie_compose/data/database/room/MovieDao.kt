package com.fakhry.movie_compose.data.database.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fakhry.movie_compose.data.database.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert
    fun insertListMovies(listMovie: List<MovieEntity>)

    @Query("SELECT * FROM movie_entities ORDER BY id_movie ASC")
    fun getListMovies(): List<MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE title LIKE '%' || :query || '%' ORDER BY id_movie ASC")
    fun getListMovies(query: String): List<MovieEntity>

    @Query("SELECT * FROM movie_entities WHERE id_movie = :movieId")
    fun getMovieDetails(movieId: Int): MovieEntity?

    @Query("DELETE FROM movie_entities WHERE id_movie = :movieId")
    fun deleteMovie(movieId: Int)
}