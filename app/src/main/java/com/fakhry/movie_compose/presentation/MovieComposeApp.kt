package com.fakhry.movie_compose.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fakhry.movie_compose.common.theme.MovieComposeTheme
import com.fakhry.movie_compose.core.navigation.Screen
import com.fakhry.movie_compose.core.navigation.keyMovieId
import com.fakhry.movie_compose.presentation.about.AboutScreen
import com.fakhry.movie_compose.presentation.details.MovieDetailsScreen
import com.fakhry.movie_compose.presentation.home.HomeScreen


@Composable
fun MovieComposeApp(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = { movieId ->
                    navController.navigate(Screen.DetailMovie.createRoute(movieId))
                },
                navigateToAbout = { navController.navigate(Screen.About.route) }
            )
        }
        composable(
            route = Screen.DetailMovie.route,
            arguments = listOf(navArgument(keyMovieId) { type = NavType.IntType }),
        ) {
            val id = it.arguments?.getInt(keyMovieId) ?: -1
            MovieDetailsScreen(
                movieId = id,
                navigateBack = { navController.navigateUp() },
            )
        }

        composable(route = Screen.About.route) {
            AboutScreen(
                navigateBack = { navController.navigateUp() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieComposeAppPreview() {
    MovieComposeTheme {
        MovieComposeApp()
    }
}
