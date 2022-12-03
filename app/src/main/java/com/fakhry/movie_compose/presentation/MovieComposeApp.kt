package com.fakhry.movie_compose.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fakhry.movie_compose.common.theme.MovieComposeTheme
import com.fakhry.movie_compose.core.navigation.Screen
import com.fakhry.movie_compose.core.navigation.keyMovieId
import com.fakhry.movie_compose.presentation.details.MovieDetailsPage
import com.fakhry.movie_compose.presentation.home.HomeScreen


@Composable
fun MovieComposeApp(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = { movieId ->
                    navController.navigate(Screen.DetailMovie.createRoute(movieId))
                }
            )
        }
        composable(
            route = Screen.DetailMovie.route,
            arguments = listOf(navArgument(keyMovieId) { type = NavType.LongType }),
        ) {
            val id = it.arguments?.getInt(keyMovieId) ?: -1
            MovieDetailsPage(
                movieId = id,
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
