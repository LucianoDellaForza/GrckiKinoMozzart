package com.luka.grcki_kino_mozzart.ui.feature_upcoming

import androidx.lifecycle.viewModelScope
import com.luka.grcki_kino_mozzart.BaseViewModel
import com.luka.grcki_kino_mozzart.EventForUi
import com.luka.grcki_kino_mozzart.data.repo.DrawRepo
import com.luka.grcki_kino_mozzart.network.NetworkResult
import com.luka.grcki_kino_mozzart.ui.model.DrawUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpcomingDrawsViewModel @Inject constructor(
    private val drawRepo: DrawRepo
): BaseViewModel<UpcomingDrawsScreenUiState, UpcomingDrawsScreenEvent>() {

    override fun initialState(): UpcomingDrawsScreenUiState {
        return UpcomingDrawsScreenUiState()
    }

    override fun onEvent(event: UpcomingDrawsScreenEvent) {
        when (event) {
            UpcomingDrawsScreenEvent.OnFetchUpcomingDraws -> {
                viewModelScope.launch { fetchDraws() }
            }
            is UpcomingDrawsScreenEvent.OnExpiredDrawItemClick -> {
                sendUiEvent(EventForUi.ShowToast(event.message))
            }
            is UpcomingDrawsScreenEvent.OnActiveDrawItemClick -> {
                sendUiEvent(UpcomingDrawsScreenEventForUi.NavigateToDrawDetails(drawUi = event.drawUi))
            }
        }
    }

    init {
//        viewModelScope.launch {
//            fetchDraws()
//        }
    }

    private suspend fun fetchDraws() {
        _state.update { it.copy(loading = true, dialog = null) }
        when (val drawsResult = drawRepo.fetchLastXDraws(numberOfDraws = 20)) {
            is NetworkResult.Error -> { _state.update { it.copy(loading = false, dialog = UpcomingDrawsScreenDialog.FetchDrawsError(drawsResult.message ?: "Error")) }}
            is NetworkResult.Exception -> { _state.update { it.copy(loading = false, dialog = UpcomingDrawsScreenDialog.FetchDrawsError(drawsResult.e.localizedMessage ?: "Error")) }}
            is NetworkResult.Success -> {
            println("fetch draws result = ${drawsResult.data}")
            _state.update { it.copy(
                loading = false,
                dialog = null,
                draws = drawsResult.data.map { drawResponse -> DrawUi(drawId = drawResponse.drawId, drawTime = drawResponse.drawTime) }
            ) }}
        }
    }

}

data class UpcomingDrawsScreenUiState(
    val loading: Boolean = true,
    val dialog: UpcomingDrawsScreenDialog? = null,

    val draws: List<DrawUi> = listOf()
)

sealed class UpcomingDrawsScreenDialog() {
    data class FetchDrawsError(val message: String) : UpcomingDrawsScreenDialog()
}

sealed interface UpcomingDrawsScreenEvent {
    data object OnFetchUpcomingDraws: UpcomingDrawsScreenEvent
    data class OnExpiredDrawItemClick(val message: String): UpcomingDrawsScreenEvent
    data class OnActiveDrawItemClick(val drawUi: DrawUi): UpcomingDrawsScreenEvent
}

sealed class UpcomingDrawsScreenEventForUi : EventForUi {
    data class NavigateToDrawDetails(val drawUi: DrawUi): UpcomingDrawsScreenEventForUi()
}