package h4rar.space.simpletodo.screens

sealed class Screen(val route: String) {
    object Main : Screen(route = "main_screen")
    object Settings : Screen(route = "detail_screen")
}
