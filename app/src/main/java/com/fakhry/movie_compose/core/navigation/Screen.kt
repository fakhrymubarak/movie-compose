package com.fakhry.movie_compose.core.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object DetailMovie : Screen("home/{$keyMovieId}") {
        fun createRoute(movieId: Int) = "home/$movieId"
    }
}
