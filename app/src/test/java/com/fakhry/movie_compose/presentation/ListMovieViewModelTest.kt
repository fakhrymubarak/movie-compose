package com.fakhry.movie_compose.presentation

import com.fakhry.movie_compose.core.utils.UiStateWrapper
import com.fakhry.movie_compose.core.utils.UiText
import com.fakhry.movie_compose.data.Resource
import com.fakhry.movie_compose.data.dummy.listMovie
import com.fakhry.movie_compose.domain.model.Movie
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

class ListMovieViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: ListMovieViewModel
    private lateinit var listMovieState: UiStateWrapper<List<Movie>>

    private var repository = mock(MovieRepository::class.java)

    @Before
    fun setUp() {
        viewModel = ListMovieViewModel(repository)
        listMovieState = UiStateWrapper.Initial
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `fetchListMovie successGetData returnTrue`() = runTest {
        // arrange
        val resourceFlow = flow {
            emit(Resource.Loading)
            kotlinx.coroutines.delay(5)
            emit(Resource.Success(listMovie))
        }
        whenever(repository.getListMovie()).thenReturn(resourceFlow)

        // act
        val observer = viewModel.listMovieState.test(this)
        viewModel.fetchListMovie()
        advanceUntilIdle()
        verify(repository).getListMovie()

        // assert
        observer.assertValuesAndFinish(
            UiStateWrapper.Initial,
            UiStateWrapper.Loading(true),
            UiStateWrapper.Loading(false),
            UiStateWrapper.Success(listMovie)
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
        whenever(repository.getListMovie()).thenReturn(resourceFlow)

        // act
        val observer = viewModel.listMovieState.test(this)
        viewModel.fetchListMovie()
        advanceUntilIdle()
        verify(repository).getListMovie()

        // assert
        observer.assertValuesAndFinish(
            UiStateWrapper.Initial,
            UiStateWrapper.Loading(true),
            UiStateWrapper.Loading(false),
            UiStateWrapper.Error(uiText = UiText.unknownError())
        )
    }
}