package com.example.simbirsoft.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simbirsoft.presentation.ui.CreateNoteScreen
import com.example.simbirsoft.presentation.ui.HomeScreen
import com.example.simbirsoft.presentation.ui.NoteDetailScreen

@Composable
fun NoteNavHost (
    navController: NavHostController = rememberNavController(),
    startDestination: Screen = Screen.Home
) {
    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = startDestination.route,
            Modifier.padding(innerPadding)
        ) {
            composable(
                Screen.Home.route // Исправленный пункт навигации для Home
            ) {
                HomeScreen(navController = navController)
            }
            composable(
                Screen.Details.route,
                arguments = listOf(navArgument("noteId") { type = NavType.IntType })
            ) { backStackEntry ->
                NoteDetailScreen(noteId = backStackEntry.arguments?.getInt("noteId") ?: 0)
            }
            composable(
                Screen.AddNote.route
            ) {
                CreateNoteScreen(navController = navController)
            }
        }
    }
}

