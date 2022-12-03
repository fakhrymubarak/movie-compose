package com.fakhry.movie_compose.data

import com.fakhry.movie_compose.core.utils.UiText
import com.fakhry.movie_compose.data.database.entity.MovieEntity
import com.fakhry.movie_compose.data.database.room.MovieDao
import com.fakhry.movie_compose.data.database.room.MovieDatabase
import com.fakhry.movie_compose.data.repository.MovieRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.*

class MovieRepositoryTest {

    private val mockDao = mock(MovieDao::class.java)
    private val mockDatabase = mock(MovieDatabase::class.java)
    private val repository = MovieRepositoryImpl(mockDatabase)

    private val listMovieEntity = MovieEntity.generateDummyMovieEntity()
    private val listMovies = listMovieEntity.map { it.toMovie() }
    private val resSuccessListMovie = Resource.Success(listMovies)

    private val movieDetailsId = 436270
    private val movieDetailsEntity = MovieEntity.generateDummyMovieEntity().first()
    private val movieDetails = movieDetailsEntity.toMovieDetails()
    private val resSuccessMovieDetails = Resource.Success(movieDetails)

    @Before
    fun setUp() {
        whenever(mockDatabase.movieDao()).doReturn(mockDao)
    }

    @Test
    fun `getListMovie dataIsEmpty successReturnGeneratedListMovies`() {
        runBlocking {
            // arrange
            whenever(mockDao.getListMovies()).doReturn(listOf())

            // act
            val result = repository.getListMovie().take(2).toList()
            verify(mockDatabase, atLeast(2)).movieDao()

            // assert
            val expectedResult = listOf(Resource.Loading, resSuccessListMovie)
            assertThat(result[0]).isEqualTo(expectedResult[0])
            assertThat(result[1]).isEqualTo(expectedResult[1])
        }
    }

    @Test
    fun `getListMovie dataIsNotEmpty successReturnListMovies`() {
        runBlocking {
            // arrange
            whenever(mockDao.getListMovies()).doReturn(listMovieEntity)

            // act
            val result = repository.getListMovie().take(2).toList()
            verify(mockDatabase).movieDao()

            // assert
            val expectedResult = listOf(Resource.Loading, resSuccessListMovie)
            assertThat(result[0]).isEqualTo(expectedResult[0])
            assertThat(result[1]).isEqualTo(expectedResult[1])
        }
    }

    @Test
    fun `getDetailsMovie dataIsEmpty failedReturnErrorMessage`() {
        runBlocking {
            // arrange
            val unknownMovieId = 69
            whenever(mockDao.getMovieDetails(unknownMovieId)).doReturn(null)

            // act
            val result = repository.getMovieDetails(unknownMovieId).take(2).toList()
            verify(mockDatabase).movieDao()

            // assert
            val resError = Resource.Error(UiText.DynamicString("Movie Tidak Ditemukan"))
            val expectedResult = listOf(Resource.Loading, resError)
            assertThat(result[0]).isEqualTo(expectedResult[0])
            assertThat(result[1]).isEqualTo(expectedResult[1])
        }
    }

    @Test
    fun `getDetailsMovie dataIsNotEmpty successReturnDetailMovies`() {
        runBlocking {
            // arrange
            whenever(mockDao.getMovieDetails(movieDetailsId)).doReturn(movieDetailsEntity)

            // act
            val result = repository.getMovieDetails(movieDetailsId).take(2).toList()
            verify(mockDatabase).movieDao()

            // assert
            val expectedResult = listOf(Resource.Loading, resSuccessMovieDetails)
            assertThat(result[0]).isEqualTo(expectedResult[0])
            assertThat(result[1]).isEqualTo(expectedResult[1])
        }
    }

    @Test(expected = Exception::class)
    fun `deleteMovie movieNotFound failedReturnErrorMessage`() {
        runBlocking {
            // arrange
            whenever(mockDao.deleteMovie(movieDetailsEntity.movieId)).doThrow(Exception())

            // act
            repository.deleteMovie(movieDetailsEntity.toMovie()).take(2).toList()
        }
    }

    @Test
    fun `deleteMovie movieDeleted successReturnTrue`() {
        runBlocking {
            // act
            val result = repository.deleteMovie(movieDetailsEntity.toMovie()).take(2).toList()
            verify(mockDatabase).movieDao()

            // assert
            val expectedResult = listOf(Resource.Loading, Resource.Success(true))
            assertThat(result[0]).isEqualTo(expectedResult[0])
            assertThat(result[1]).isEqualTo(expectedResult[1])
        }
    }
}
