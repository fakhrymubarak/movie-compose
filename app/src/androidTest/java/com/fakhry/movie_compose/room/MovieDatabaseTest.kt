package com.fakhry.movie_compose.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fakhry.movie_compose.data.database.entity.MovieEntity
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) // Annotate with @RunWith
class MovieDatabaseTest {
    private lateinit var db: MovieDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java).build()
        dao = db.movieDao()
    }

    @After
    fun closDb() {
        db.close()
        db.clearAllTables()
    }

    @Test
    fun insertListMovie_thenGetListMovie_successInsertAndGet() {
        val listMovieEntity = MovieEntity.generateDummyMovieEntity()
        dao.insertListMovies(listMovieEntity)

        val listMovieFromDao = dao.getListMovies()
        assertThat(listMovieFromDao).isEqualTo(listMovieEntity)
    }

    @Test
    fun insertListMovie_thenDeleteMovie_success() {
        val listMovieEntity = MovieEntity.generateDummyMovieEntity()
        dao.insertListMovies(listMovieEntity)

        val movieEntityFromDao = dao.getListMovies().first()

        dao.deleteMovie(movieEntityFromDao.movieId)
        val listMovieFromDao = dao.getListMovies()

        assertThat(listMovieFromDao).doesNotContain(movieEntityFromDao)
    }

    @Test
    fun insertListMovie_thenDoesNotDeleteMovie_success() {
        val listMovieEntity = MovieEntity.generateDummyMovieEntity()
        dao.insertListMovies(listMovieEntity)
        val movieEntityFromDao = dao.getListMovies().first()
        val listMovieFromDao = dao.getListMovies()
        assertThat(listMovieFromDao).contains(movieEntityFromDao)
    }

    @Test
    fun insertListMovie_thenGetMovieDetails_success() {
        val listMovieEntity = MovieEntity.generateDummyMovieEntity()
        dao.insertListMovies(listMovieEntity)
        val movieEntityFromListDao = dao.getListMovies().first()
        val movieEntityFromDao = dao.getMovieDetails(movieEntityFromListDao.movieId)
        assertThat(movieEntityFromListDao).isEqualTo(movieEntityFromDao)
    }
}