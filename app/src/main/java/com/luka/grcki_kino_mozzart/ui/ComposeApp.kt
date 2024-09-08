package com.luka.grcki_kino_mozzart.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.luka.grcki_kino_mozzart.ui.navigation.root.Graph
import com.luka.grcki_kino_mozzart.ui.navigation.root.RootNavigationGraph

@Composable
fun ComposeApp() {
    val rootNavController = rememberNavController()
    RootNavigationGraph(
        navController = rootNavController,
        startDestination = Graph.UPCOMING
    )
}