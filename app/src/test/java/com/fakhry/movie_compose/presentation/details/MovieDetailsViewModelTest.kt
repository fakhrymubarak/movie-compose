package com.fakhry.movie_compose.presentation.details

import com.fakhry.movie_compose.core.utils.UiState
import com.fakhry.movie_compose.core.utils.UiText
import com.fakhry.movie_compose.data.Resource
import com.fakhry.movie_compose.data.dummy.movieDetails
import com.fakhry.movie_compose.domain.model.MovieDetails
import com.fakhry.movie_compose.domain.repository.MovieRepository
import com.fakhry.movie_compose.helpers.MainCoroutineRule
import com.fakhry.movie_compose.helpers.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MovieDetailsViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movieDetailsState: UiState<MovieDetails>

    private var repository = mock(MovieRepository::class.java)

    @Before
    fun setUp() {
        viewModel = MovieDetailsViewModel(repository)
        movieDetailsState = UiState.Initial
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetchListMovie successGetData returnTrue`() = runTest {
        // arrange
        val resourceFlow = flow {
            emit(Resource.Loading)
            kotlinx.coroutines.delay(5)
            emit(Resource.Success(movieDetails))
        }
        whenever(repository.getMovieDetails(movieDetails.id)).thenReturn(resourceFlow)

        // act
        val observer = viewModel.movieDetailsState.test(this)
        viewModel.fetchMovieDetails(movieDetails.id)
        advanceUntilIdle()
        verify(repository).getMovieDetails(movieDetails.id)

        // assert
        observer.assertValuesAndFinish(
            UiState.Initial,
            UiState.Loading(true),
            UiState.Loading(false),
            UiState.Success(movieDetails)
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetchListMovie errorUnknown returnMessageUnknown`() = runTest {
        // arrange
        val resourceFlow = flow {
            emit(Resource.Loading)
            kotlinx.coroutines.delay(5)
            emit(Resource.Error(uiText = UiText.unknownError()))
        }
        whenever(repository.getMovieDetails(movieDetails.id)).thenReturn(resourceFlow)

        // act
        val observer = viewModel.movieDetailsState.test(this)
        viewModel.fetchMovieDetails(movieDetails.id)
        advanceUntilIdle()
        verify(repository).getMovieDetails(movieDetails.id)

        // assert
        observer.assertValuesAndFinish(
            UiState.Initial,
            UiState.Loading(true),
            UiState.Loading(false),
            UiState.Error(uiText = UiText.unknownError())
        )
    }
}