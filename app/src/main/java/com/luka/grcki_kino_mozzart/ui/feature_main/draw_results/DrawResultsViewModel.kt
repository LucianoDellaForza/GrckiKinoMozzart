package com.luka.grcki_kino_mozzart.ui.feature_main.draw_results

import androidx.lifecycle.viewModelScope
import com.luka.grcki_kino_mozzart.BaseViewModel
import com.luka.grcki_kino_mozzart.EventForUi
import com.luka.grcki_kino_mozzart.data.repo.DrawRepo
import com.luka.grcki_kino_mozzart.network.NetworkResult
import com.luka.grcki_kino_mozzart.ui.model.DrawResultUI
import com.luka.grcki_kino_mozzart.ui.model.WinningNumber
import com.luka.grcki_kino_mozzart.utils.getFormattedDate
import com.luka.grcki_kino_mozzart.utils.getXDaysInPastFromDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class DrawResultsViewModel @Inject constructor(
    private val drawRepo: DrawRepo
): BaseViewModel<DrawResultsScreenUiState, DrawResultsScreenEvent>() {

    override fun initialState(): DrawResultsScreenUiState {
        return DrawResultsScreenUiState()
    }

    override fun onEvent(event: DrawResultsScreenEvent) {
        when (event) {
            DrawResultsScreenEvent.OnRetryFetchDrawResults -> {
                viewModelScope.launch {
                    fetchDrawResultsLast24h()
                }
            }

            DrawResultsScreenEvent.OnNavigateBack -> {
                sendUiEvent(DrawResultsScreenEventForUi.NavigateBack)
            }
        }
    }

    init {
        viewModelScope.launch {
            fetchDrawResultsLast24h()
        }
    }

    private suspend fun fetchDrawResultsLast24h() {
        _state.update { it.copy(loading = true, dialog = null) }
        val currentDate = Date()
        val result = drawRepo.fetchDrawResultsForDateRange(
            fromDate = getFormattedDate(date = getXDaysInPastFromDate(currentDate, 0), dateFormat = "yyyy-MM-dd"),
            toDate = getFormattedDate(date = getXDaysInPastFromDate(currentDate, 0), dateFormat = "yyyy-MM-dd"),
        )
        when (result) {
            is NetworkResult.Error -> { _state.update { it.copy(loading = false, dialog = DrawResultsScreenDialog.FetchDrawResultsError(result.message ?: "Error")) }}
            is NetworkResult.Exception -> { _state.update { it.copy(loading = false, dialog = DrawResultsScreenDialog.FetchDrawResultsError(result.e.localizedMessage ?: "Error")) }}
            is NetworkResult.Success -> {
                _state.update { it.copy(
                    loading = false,
                    dialog = null,
                    finishedDraws = result.data.content.map { drawResultsInfo ->
                        val winningNumbers = drawResultsInfo.winningNumbers.numbers.toMutableList()
                        // Add 0-s to make list size dividable by 3 to align row items easier
                        while (winningNumbers.size % 7 != 0) {
                            winningNumbers.add(0)
                        }
                        DrawResultUI(
                            drawId = drawResultsInfo.drawId,
                            drawTime = drawResultsInfo.drawTime,
                            winningNumbers = winningNumbers.map { number ->
                                WinningNumber(number = number)
                            }
                        )
                    }
                ) }
            }
        }
    }

}

data class DrawResultsScreenUiState(
    val loading: Boolean = true,
    val dialog: DrawResultsScreenDialog? = null,

    val finishedDraws: List<DrawResultUI> = listOf()
)

sealed class DrawResultsScreenDialog() {
    data class FetchDrawResultsError(val message: String) : DrawResultsScreenDialog()
}

sealed interface DrawResultsScreenEvent {
    data object OnRetryFetchDrawResults: DrawResultsScreenEvent
    data object OnNavigateBack: DrawResultsScreenEvent
}

sealed class DrawResultsScreenEventForUi : EventForUi {
    data object NavigateBack : DrawResultsScreenEventForUi()
}
