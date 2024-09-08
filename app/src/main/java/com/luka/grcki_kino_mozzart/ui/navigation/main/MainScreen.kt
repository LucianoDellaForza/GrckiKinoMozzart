package com.luka.grcki_kino_mozzart.ui.navigation.main

import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.luka.grcki_kino_mozzart.R
import com.luka.grcki_kino_mozzart.ui.feature_main.TabBarItem
import com.luka.grcki_kino_mozzart.ui.feature_main.draw_play.TopNavigationBar

@Composable
fun MainScreen(
    navigateBack: () -> Unit,
    // These 2 params are used only by PlayDrawScreen
    drawId: Long,
    drawTime: Long
) {
    val mainScreenState = rememberMainScreenState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            val tabs = remember {
                listOf(
                    TabBarItem(title = R.string.tab_item_1, icon = R.drawable.grid_view, route = MainDestinations.PLAY_DRAW_SCREEN),
                    TabBarItem(title = R.string.tab_item_2, icon = R.drawable.add_circle, route = ""),
                    TabBarItem(title = R.string.tab_item_3, icon = R.drawable.play_circle, route = MainDestinations.LIVE_DRAW_SCREEN),
                    TabBarItem(title = R.string.tab_item_4, icon = R.drawable.history, route = MainDestinations.DRAW_RESULTS_SCREEN),
                    TabBarItem(title = R.string.tab_item_5, icon = R.drawable.avg_pace, route = "")
                )
            }
            var selectedTabIndex by remember { mutableIntStateOf(0) }

            LaunchedEffect(key1 = mainScreenState.navController) {
                mainScreenState.navController.addOnDestinationChangedListener { _, destination, _ ->
                    selectedTabIndex = tabs.indexOfFirst { it.route == destination.route }
                }
            }

            TopNavigationBar(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabClick = { tabIndex, route ->
                    when {
                        route.isEmpty() -> {
                            Toast.makeText(context, context.getString(R.string.not_available_toast), Toast.LENGTH_SHORT).show()
                        }
                        route != mainScreenState.currentRoute -> {
                            selectedTabIndex = tabIndex
                            mainScreenState.popAllAndNavigateToDestination(route)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        MainGraph(
            mainScreenState = mainScreenState,
            paddingValues = paddingValues,
            navigateBackToUpcomingDraws = navigateBack,
            drawId = drawId,
            drawTime = drawTime,
        )
    }
}