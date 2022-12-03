package com.fakhry.movie_compose.di

import android.content.Context
import com.fakhry.movie_compose.data.database.room.MovieDatabase
import com.fakhry.movie_compose.data.repository.MovieRepositoryImpl
import com.fakhry.movie_compose.domain.repository.MovieRepository

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)
        return MovieRepositoryImpl.getInstance(database)
    }
}