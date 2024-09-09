package com.luka.grcki_kino_mozzart.ui.feature_main.draw_play

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luka.grcki_kino_mozzart.EventForUi
import com.luka.grcki_kino_mozzart.R
import com.luka.grcki_kino_mozzart.ui.common.component.DrawInfoLayout
import com.luka.grcki_kino_mozzart.ui.common.dialog.ErrorDialog
import com.luka.grcki_kino_mozzart.ui.common.dialog.LoadingDialog
import com.luka.grcki_kino_mozzart.ui.feature_main.TabBarItem
import com.luka.grcki_kino_mozzart.ui.feature_upcoming.UpcomingDrawsScreenEventForUi
import com.luka.grcki_kino_mozzart.ui.theme.MozzartBlue

@Composable
fun PlayDrawScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayDrawViewModel,
    drawId: Long,
    drawTime: Long,
    navigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(PlayDrawScreenEvent.OnInitDrawInfo(drawId = drawId, drawTime = drawTime))

        viewModel.uiEvents.collect { event ->
            when (event) {
                is EventForUi.ShowToast -> {
                    Toast.makeText(context, event.message, event.toastLength).show()
                }
                is UpcomingDrawsScreenEventForUi.NavigateToDrawDetails -> {
                    navigateBack()
                }
            }
        }
    }

    when (val dialogInfo = state.dialog) {
        is PlayDrawScreenDialog.DrawExpired -> {
            // Show draw expired dialog
            ErrorDialog(
                message = stringResource(id = dialogInfo.message, dialogInfo.drawId),
                btnText = stringResource(id = R.string.back_btn_text),
                onBtnClick = { navigateBack() },
                onDismiss = { navigateBack() }
            )
        }
        null -> {}
    }

    if (state.loading) {
        LoadingDialog(onDismiss = {})
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        DrawInfoLayout(
            modifier = Modifier.padding(8.dp),
            drawId = state.drawId,
            drawTime = state.drawTime

        )
        HeaderOddsInformation(
            modifier = Modifier.fillMaxWidth(),
        )
        PickBallNumbersLayout(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            ballLimit = state.selectedBallNumberLimit,
            selectedNumbers = state.ballNumbers,
            onRandomizeBallNumbers = {
                viewModel.onEvent(PlayDrawScreenEvent.OnRandomizeBallNumbers)
            },
            onBallLimitChange = { ballLimit ->
                viewModel.onEvent(PlayDrawScreenEvent.OnChangeBallLimit(ballLimit = ballLimit))
            },
            onBallNumberClick = { ballNumber ->
                viewModel.onEvent(PlayDrawScreenEvent.OnBallNumberClicked(ballNumber = ballNumber))
            }
        )
    }
}

@Composable
fun TopNavigationBar(
    tabs: List<TabBarItem>,
    selectedTabIndex: Int,
    onTabClick: (tabIndex: Int, route: String) -> Unit,
) {
    NavigationBar(
        containerColor = MozzartBlue,
    ) {
        tabs.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    onTabClick(index, tabBarItem.route)
                },
                icon = {
                    Icon(
                        modifier = Modifier.sizeIn(maxHeight = 35.dp),
                        imageVector = ImageVector.vectorResource(id = tabBarItem.icon),
                        contentDescription = null,
                        tint = if (selectedTabIndex == index) Color.Yellow else Color.White
                    )
                },
                label = {
                    Box(
                        modifier = Modifier.height(35.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = tabBarItem.title),
                            textAlign = TextAlign.Center,
                            color = if (selectedTabIndex == index) Color.Yellow else Color.White
                        )
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIndicatorColor = MozzartBlue,
                    selectedTextColor = MozzartBlue,
                    selectedIconColor = MozzartBlue,
                    unselectedIconColor = Color.White,
                    unselectedTextColor = Color.White,
                    disabledIconColor = MozzartBlue,
                    disabledTextColor = MozzartBlue
                )
            )
        }
    }
}

@Composable
private fun HeaderOddsInformation(
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf(
            Pair("B.K", "Kvota"),
            Pair("1", "3.75"),
            Pair("2", "14"),
            Pair("3", "65"),
            Pair("4", "275"),
            Pair("5", "1350"),
            Pair("6", "6500"),
            Pair("7", "25000")
        ).forEachIndexed() { index, pair ->
            OddsInfoItem(
                modifier = Modifier.weight(1f),
                topText = pair.first,
                bottomText = pair.second,
                showDivider = index != 0
            )
        }
    }
}









