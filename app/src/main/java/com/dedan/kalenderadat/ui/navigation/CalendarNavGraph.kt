package com.dedan.kalenderadat.ui.navigation

import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dedan.kalenderadat.ui.screen.home.HomeDestination
import com.dedan.kalenderadat.ui.screen.home.HomeScreen
import com.dedan.kalenderadat.ui.screen.note.NoteEditorDestination
import com.dedan.kalenderadat.ui.screen.note.NoteEditorScreen

@Composable
fun CalendarNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToNoteEditor = {
                    navController.navigate("note/$it")
                }
            )
        }
        composable(
            route = NoteEditorDestination.routeWithArgs,
            arguments = listOf(navArgument(NoteEditorDestination.dateArg) {
                type = NavType.StringType
            }),
            exitTransition = { ExitTransition.None }
        ) {
            NoteEditorScreen(
                navigateBack = { navController.popBackStack() },
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}