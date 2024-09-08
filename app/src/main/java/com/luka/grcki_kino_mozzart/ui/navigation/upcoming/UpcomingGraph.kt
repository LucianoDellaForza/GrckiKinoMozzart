package com.luka.grcki_kino_mozzart.ui.navigation.upcoming

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.luka.grcki_kino_mozzart.ui.navigation.root.Graph
import com.luka.grcki_kino_mozzart.ui.navigation.upcoming.UpcomingDestinations.UPCOMING_DRAWS_SCREEN
import com.luka.grcki_kino_mozzart.ui.feature_upcoming.UpcomingDrawsScreen
import com.luka.grcki_kino_mozzart.ui.model.DrawUi

fun NavGraphBuilder.upcomingNavGraph(
    navigateToPlayDrawScreen: (drawUi: DrawUi) -> Unit
) {
    navigation(
        route = Graph.UPCOMING,
        startDestination = UPCOMING_DRAWS_SCREEN
    ) {
        composable(route = UPCOMING_DRAWS_SCREEN) {
            UpcomingDrawsScreen(
                viewModel = hiltViewModel(),
                navigateToPlayDrawScreen = { drawUI ->
                    navigateToPlayDrawScreen(drawUI)
                }
            )
        }
    }
}

object UpcomingDestinations {
    const val UPCOMING_DRAWS_SCREEN = "upcoming_draws_screen"
}