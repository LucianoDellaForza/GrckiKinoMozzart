package com.luka.grcki_kino_mozzart.ui.navigation.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.luka.grcki_kino_mozzart.ui.navigation.main.MainScreen
import com.luka.grcki_kino_mozzart.ui.navigation.upcoming.upcomingNavGraph

@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = startDestination
    ) {
        upcomingNavGraph(
            navigateToPlayDrawScreen = { drawUi ->
                val routeWithNavArgs = "${Graph.MAIN}/${drawUi.drawId}?drawTime=${drawUi.drawTime}"
                navController.navigate(routeWithNavArgs)
            }
        )

        //TODO: implement DB or DataStore/SharedPref and pass only drawId as navigation argument
        composable(
            route = "${Graph.MAIN}/{drawId}?drawTime={drawTime}",
            arguments = listOf(
                navArgument("drawId") {
                    type = NavType.LongType
                },
                navArgument("drawTime") {
                    type = NavType.LongType
                },
            )
        ) {navBackStackEntry ->
            val drawId = navBackStackEntry.arguments?.getLong("drawId")
            val drawTime = navBackStackEntry.arguments?.getLong("drawTime")

            if (drawId != null && drawTime != null) {
                MainScreen(
                    navigateBack = {
                        navController.navigate(startDestination) {
                            this.popUpTo(navController.graph.id) {
                                inclusive = true
                            }
                        }
                    },
                    drawId = drawId,
                    drawTime = drawTime
                )
            }
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val UPCOMING = "upcoming_graph"
    const val MAIN = "main_graph"
}