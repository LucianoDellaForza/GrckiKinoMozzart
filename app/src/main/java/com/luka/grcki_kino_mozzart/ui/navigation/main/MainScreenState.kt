package com.luka.grcki_kino_mozzart.ui.navigation.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberMainScreenState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(coroutineScope) {
    MainScreenState(
        navController = navController,
        snackbarHostState = snackbarHostState,
        coroutineScope = coroutineScope
    )
}

@Stable
class MainScreenState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope
) {

    val currentRoute: String?
        get() = navController.currentDestination?.route


    fun navigateBack() {
        navController.popBackStack()
    }

    fun popAllAndNavigateToDestination(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(navController.graph.startDestinationId) {
                inclusive = false
            }
        }
    }

}