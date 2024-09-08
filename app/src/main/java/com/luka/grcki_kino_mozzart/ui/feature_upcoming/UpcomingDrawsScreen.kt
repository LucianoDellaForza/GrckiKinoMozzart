package com.luka.grcki_kino_mozzart.ui.feature_upcoming

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luka.grcki_kino_mozzart.EventForUi
import com.luka.grcki_kino_mozzart.R
import com.luka.grcki_kino_mozzart.ui.common.dialog.ErrorDialog
import com.luka.grcki_kino_mozzart.ui.common.dialog.LoadingDialog
import com.luka.grcki_kino_mozzart.ui.model.DrawUi

@Composable
fun UpcomingDrawsScreen(
    modifier: Modifier = Modifier,
    viewModel: UpcomingDrawsViewModel,
    navigateToPlayDrawScreen: (drawUi: DrawUi) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.onEvent(UpcomingDrawsScreenEvent.OnFetchUpcomingDraws)

        viewModel.uiEvents.collect { event ->
            when (event) {
                is EventForUi.ShowToast -> {
                    Toast.makeText(context, event.message, event.toastLength).show()
                }
                is UpcomingDrawsScreenEventForUi.NavigateToDrawDetails -> {
                    navigateToPlayDrawScreen(event.drawUi)
                }
            }
        }
    }

    when (val dialogInfo = state.dialog) {
        is UpcomingDrawsScreenDialog.FetchDrawsError -> {
            ErrorDialog(
                message = dialogInfo.message,
                btnText = stringResource(id = R.string.retry_btn_text),
                onBtnClick = { viewModel.onEvent(UpcomingDrawsScreenEvent.OnFetchUpcomingDraws) },
                onDismiss = { viewModel.onEvent(UpcomingDrawsScreenEvent.OnFetchUpcomingDraws) }
            )
        }
        null -> {}
    }

    if (state.loading) {
        LoadingDialog(
            onDismiss = {}
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        HeaderLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp)
        )
        DrawItems(
            modifier = Modifier.weight(1f),
            draws = state.draws,
            onDrawItemClick = { drawItem, isTimedOut ->
                if (isTimedOut) {
                    viewModel.onEvent(UpcomingDrawsScreenEvent.OnExpiredDrawItemClick(message = context.getString(R.string.expired_draw_toast)))
                } else {
                    viewModel.onEvent(UpcomingDrawsScreenEvent.OnActiveDrawItemClick(drawUi = drawItem))
                }
            },
            onDrawTimedOut = {
                viewModel.onEvent(UpcomingDrawsScreenEvent.OnFetchUpcomingDraws)
            }
        )
//        Button(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
////                .padding(horizontal = 16.dp),
//            shape = RoundedCornerShape(0.dp),
//            onClick = {
//                viewModel.onEvent(UpcomingDrawsScreenEvent.OnFetchUpcomingDraws)
//            }
//        ) {
//            Text(
//                text = stringResource(id = R.string.refresh_btn_text)
//            )
//        }
    }
}

@Composable
private fun HeaderLayout(
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // TODO: Add Icon
            Text(text = stringResource(id = R.string.upcoming_draws_header_title))
        }
        HorizontalDivider(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.upcoming_draws_time_title)
            )
            Text(
                text = stringResource(id = R.string.upcoming_draws_countdown_title)
            )
        }
    }
}

@Composable
private fun DrawItems(
    modifier: Modifier = Modifier,
    draws: List<DrawUi>,
    onDrawItemClick: (drawItem: DrawUi, isExpired: Boolean) -> Unit,
    onDrawTimedOut: () -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(draws) { index, drawItem ->
            key(drawItem.drawId) {
                UpcomingDrawItem(
                    drawItem = drawItem,
                    onDrawItemClick = { drawItem, isExpired ->
                        onDrawItemClick(drawItem, isExpired)
                    },
                    onDrawTimedOut = {
                        onDrawTimedOut()
                    }
                )
                if (index != draws.size - 1) {
                    HorizontalDivider()
                }
            }
        }
    }
}
