package h4rar.space.simpletodo

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import h4rar.space.simpletodo.screens.SetupNavGraph
import h4rar.space.simpletodo.ui.theme.SimpleTodoTheme
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleTodoTheme {
                navController = rememberNavController()

                val context = LocalContext.current
                val mViewModel: MainViewModel = viewModel(
                    factory = MainViewModelFactory(context.applicationContext as Application)
                )
                mViewModel.initDatabase {}

                SetupNavGraph(
                    navController = navController,
                    viewModel = mViewModel
                )
            }
        }
    }
}