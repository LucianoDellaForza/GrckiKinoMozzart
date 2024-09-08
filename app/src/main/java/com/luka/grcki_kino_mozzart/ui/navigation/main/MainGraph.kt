package com.luka.grcki_kino_mozzart.ui.navigation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.luka.grcki_kino_mozzart.ui.feature_main.draw_live.LiveDrawScreen
import com.luka.grcki_kino_mozzart.ui.feature_main.draw_play.PlayDrawScreen
import com.luka.grcki_kino_mozzart.ui.feature_main.draw_results.DrawResultsScreen
import com.luka.grcki_kino_mozzart.ui.navigation.root.Graph

@Composable
fun MainGraph(
    mainScreenState: MainScreenState,
    paddingValues: PaddingValues,
    navigateBackToUpcomingDraws: () -> Unit,
    drawId: Long,
    drawTime: Long
) {
    NavHost(
        navController = mainScreenState.navController,
        route = Graph.MAIN,
        startDestination = MainDestinations.PLAY_DRAW_SCREEN,
    ) {

        composable(MainDestinations.PLAY_DRAW_SCREEN) {
            PlayDrawScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = hiltViewModel(),
                drawId = drawId,
                drawTime = drawTime,
                navigateBack = {
                    navigateBackToUpcomingDraws()
                }
            )
        }

        composable(MainDestinations.LIVE_DRAW_SCREEN) { backStackEntry ->
            LiveDrawScreen(
                modifier = Modifier.padding(paddingValues),
            )
        }

        composable(MainDestinations.DRAW_RESULTS_SCREEN) { backStackEntry ->
            DrawResultsScreen(
                modifier = Modifier.padding(paddingValues),
                viewModel = hiltViewModel(),
                navigateBack = {
                    mainScreenState.navigateBack()
                }
            )
        }
    }
}

object MainDestinations {
    const val PLAY_DRAW_SCREEN = "play_draw_screen"
    const val LIVE_DRAW_SCREEN = "live_draw_screen"
    const val DRAW_RESULTS_SCREEN = "draw_results_screen"
}