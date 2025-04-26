package com.example.filmoteq


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.filmoteq.ui.screen.AddEditFilmScreen
import com.example.filmoteq.ui.screen.FilmListScreen
import com.example.filmoteq.ui.theme.FilmoteqTheme
import com.example.filmoteq.viewmodel.FilmViewModel
import java.util.UUID

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FilmoteqTheme(darkTheme = true) {
                val navController = rememberNavController()
                val filmViewModel: FilmViewModel by viewModels()

                NavHost(navController, startDestination = "list") {
                    composable("list") {
                        FilmListScreen(
                            filmViewModel = filmViewModel,
                            onAdd = { navController.navigate("edit") },
                            onEdit = { id -> navController.navigate("edit?filmId=$id") }
                        )
                    }
                    composable(
                        "edit?filmId={filmId}",
                        arguments = listOf(navArgument("filmId") {
                            type = NavType.StringType; nullable = true; defaultValue = null
                        })
                    ) { backStackEntry ->
                        val filmId = backStackEntry.arguments
                            ?.getString("filmId")
                            ?.let { UUID.fromString(it) }
                        val film = filmId?.let { filmViewModel.getFilmById(it) }
                        AddEditFilmScreen(
                            film = film,
                            onSave = { f ->
                                filmViewModel.addOrUpdateFilm(f)
                                navController.popBackStack()
                            },
                            onCancel = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
