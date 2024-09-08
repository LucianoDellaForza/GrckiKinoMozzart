package com.luka.grcki_kino_mozzart.ui.feature_main.draw_results

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luka.grcki_kino_mozzart.EventForUi
import com.luka.grcki_kino_mozzart.R
import com.luka.grcki_kino_mozzart.ui.common.component.DrawInfoLayout
import com.luka.grcki_kino_mozzart.ui.common.dialog.ErrorDialog
import com.luka.grcki_kino_mozzart.ui.common.dialog.LoadingDialog

@Composable
fun DrawResultsScreen(
    modifier: Modifier = Modifier,
    viewModel: DrawResultsViewModel,
    navigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is EventForUi.ShowToast -> {
                    Toast.makeText(context, event.message, event.toastLength).show()
                }
                is DrawResultsScreenEventForUi.NavigateBack -> {
                    navigateBack()
                }
            }
        }
    }

    if (state.loading) {
        LoadingDialog(
            onDismiss = {}
        )
    }

    when (val dialogInfo = state.dialog) {
        is DrawResultsScreenDialog.FetchDrawResultsError -> {
            ErrorDialog(
                message = dialogInfo.message,
                btnText = stringResource(id = R.string.back_btn_text),
                onBtnClick = {
//                    viewModel.onEvent(DrawResultsScreenEvent.OnRetryFetchDrawResults)
                    viewModel.onEvent(DrawResultsScreenEvent.OnNavigateBack)
                 },
                onDismiss = {
                    // not dismissible
//                    viewModel.onEvent(DrawResultsScreenEvent.OnRetryFetchDrawResults)
                }
            )
        }
        null -> {}
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
    ) {
        items(state.finishedDraws) { drawResultUi ->
            DrawInfoLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray),
                drawId = drawResultUi.drawId,
                drawTime = drawResultUi.drawTime
            )
            DrawResultItem(
                modifier = Modifier.fillMaxWidth(),
                winningNumbers = drawResultUi.winningNumbers
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
