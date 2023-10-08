//package h4rar.space.simpletodo.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import h4rar.space.simpletodo.MainViewModel
//import h4rar.space.simpletodo.screens.AddScreen
//import h4rar.space.simpletodo.screens.MainScreen
//import h4rar.space.simpletodo.screens.NoteScreen
//import h4rar.space.simpletodo.utils.Constants
//import h4rar.space.simpletodo.utils.Constants.Screens.ADD_SCREEN
//import h4rar.space.simpletodo.utils.Constants.Screens.MAIN_SCREEN
//import h4rar.space.simpletodo.utils.Constants.Screens.NOTE_SCREEN
//
//sealed class NavRoute(val route: String) {
//    object Main : NavRoute(MAIN_SCREEN)
//    object Add : NavRoute(ADD_SCREEN)
//    object Note : NavRoute(NOTE_SCREEN)
//}
//
//@Composable
//fun NoteNavHost(mViewModel: MainViewModel) {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = NavRoute.Main.route) {
//        composable(NavRoute.Main.route) {
//            MainScreen(
//                navController = navController,
//                viewModel = mViewModel
//            )
//        }
//        composable(NavRoute.Add.route) {
//            AddScreen(
//                navController = navController,
//                viewModel = mViewModel
//            )
//        }
//        composable(NavRoute.Note.route + "/{${Constants.Keys.ID}}") { backStackEntry ->
//            NoteScreen(
//                navController = navController,
//                viewModel = mViewModel,
//                noteId = backStackEntry.arguments?.getString(Constants.Keys.ID)
//            )
//        }
//    }
//}