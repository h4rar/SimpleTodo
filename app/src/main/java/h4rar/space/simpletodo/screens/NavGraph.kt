package h4rar.space.simpletodo.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import h4rar.space.simpletodo.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(
            route = Screen.Main.route
        ) {
            MainScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            route = Screen.Settings.route
        ) {
            SettingsScreen(viewModel = viewModel, navController = navController)
        }
    }
}