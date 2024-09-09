package com.luka.grcki_kino_mozzart.ui.feature_main.draw_play

import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.luka.grcki_kino_mozzart.BaseViewModel
import com.luka.grcki_kino_mozzart.EventForUi
import com.luka.grcki_kino_mozzart.R
import com.luka.grcki_kino_mozzart.ui.feature_main.draw_play.PlayDrawViewModel.Companion.TOTAL_BALL_COUNT
import com.luka.grcki_kino_mozzart.ui.feature_main.draw_play.model.BallNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayDrawViewModel @Inject constructor(
//    private val drawRepo: DrawRepo
): BaseViewModel<PlayDrawScreenUiState, PlayDrawScreenEvent>() {

    companion object {
        const val TOTAL_BALL_COUNT = 80
        val BALL_LIMITS = listOf(7, 8, 9, 10, 11, 12, 13, 14, 15)
    }

    private val selectedNumbers = ArrayDeque<Int>()


    override fun initialState(): PlayDrawScreenUiState {
        return PlayDrawScreenUiState()
    }

    override fun onEvent(event: PlayDrawScreenEvent) {
        when (event) {
            is PlayDrawScreenEvent.OnInitDrawInfo -> initDrawRound(drawId = event.drawId, drawTime = event.drawTime)
            PlayDrawScreenEvent.OnRandomizeBallNumbers -> pickRandomBallNumbers()
            is PlayDrawScreenEvent.OnBallNumberClicked -> onBallNumberClick(number = event.ballNumber.number)
            is PlayDrawScreenEvent.OnChangeBallLimit -> onBallLimitChange(ballLimit = event.ballLimit)
        }
    }

    private fun initDrawRound(drawId: Long, drawTime: Long) {
        viewModelScope.launch {
            _state.update { it.copy(
                loading = false,
                drawId = drawId,
                drawTime = drawTime
            ) }

            // start expiration countdown
            var remainingTimeMillis = drawTime - System.currentTimeMillis()
            while (remainingTimeMillis > 0) {
                remainingTimeMillis = drawTime - System.currentTimeMillis()
                delay(1000L)
            }
            _state.update { it.copy(dialog = PlayDrawScreenDialog.DrawExpired(R.string.draw_play_expired_toast, drawId)) }
        }
    }

    private fun pickRandomBallNumbers() {
        resetBalls()

        // Generate random numbers
        val ballNumberLimit = state.value.selectedBallNumberLimit
        val randomNumbers = (1..TOTAL_BALL_COUNT).shuffled().take(ballNumberLimit)
        selectedNumbers.clear()
        selectedNumbers.addAll(randomNumbers)

        // Update generated numbers selection state
        val updatedBalls = state.value.ballNumbers.map { ball ->
            if (ball.number in selectedNumbers) {
                ball.copy(isSelected = true)
            } else {
                ball
            }
        }

        _state.update {
            it.copy(ballNumbers = updatedBalls)
        }
    }

    private fun onBallNumberClick(number: Int) {
        val allBalls = state.value.ballNumbers.toMutableList()
        val ballNumberLimit = state.value.selectedBallNumberLimit

        val clickedBall = allBalls.find { it.number == number } ?: return
        if (!clickedBall.isSelected) {
            // If the ball limit is reached, remove the first selected number from stack
            if (selectedNumbers.size == ballNumberLimit) {
                val removedNumber = selectedNumbers.removeFirst()
                val ballToDeselect = allBalls.find { it.number == removedNumber }
                if (ballToDeselect != null) {
                    val deselectIndex = allBalls.indexOf(ballToDeselect)
                    allBalls[deselectIndex] = ballToDeselect.copy(isSelected = false)
                }
            }
            // Add the clicked ball to stack
            selectedNumbers.addLast(clickedBall.number)
        } else {
            // If the ball was already selected, remove it from stack
            selectedNumbers.remove(clickedBall.number)
        }

        // Update the clicked ball's selection state
        val clickedBallIndex = allBalls.indexOf(clickedBall)
        allBalls[clickedBallIndex] = clickedBall.copy(isSelected = !clickedBall.isSelected)

        _state.update {
            it.copy(ballNumbers = allBalls)
        }
    }

    private fun onBallLimitChange(ballLimit: Int) {
        if (ballLimit < state.value.selectedBallNumberLimit) {
            resetBalls()
        }
        _state.update { it.copy(selectedBallNumberLimit = ballLimit) }
    }

    private fun resetBalls() {
        selectedNumbers.clear()

        val allBalls = (1..TOTAL_BALL_COUNT).map { BallNumber(number = it, isSelected = false) }.toMutableList()
        _state.update {
            it.copy(ballNumbers = allBalls)
        }
    }
}

data class PlayDrawScreenUiState(
    val loading: Boolean = true,
    val drawId: Long = 0,
    val drawTime: Long = 0,

    val ballNumbers: List<BallNumber> = (1..TOTAL_BALL_COUNT).map { BallNumber(number = it) },
    val selectedBallNumberLimit: Int = 7, // initial limit

    val dialog: PlayDrawScreenDialog? = null,
)

sealed class PlayDrawScreenDialog() {
    data class DrawExpired(@StringRes val message: Int, val drawId: Long) : PlayDrawScreenDialog()
}

sealed interface PlayDrawScreenEvent {
    data class OnInitDrawInfo(val drawId: Long, val drawTime: Long): PlayDrawScreenEvent
    data object OnRandomizeBallNumbers: PlayDrawScreenEvent
    data class OnBallNumberClicked(val ballNumber: BallNumber): PlayDrawScreenEvent
    data class OnChangeBallLimit(val ballLimit: Int): PlayDrawScreenEvent
}

sealed class PlayDrawScreenEventForUi : EventForUi {
    data object NavigateBack: PlayDrawScreenEventForUi()
}

