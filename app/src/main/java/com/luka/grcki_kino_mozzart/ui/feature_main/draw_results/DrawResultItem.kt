package com.luka.grcki_kino_mozzart.ui.feature_main.draw_results

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luka.grcki_kino_mozzart.ui.model.WinningNumber

@Composable
fun DrawResultItem(
    modifier: Modifier,
    winningNumbers: List<WinningNumber>
) {
    Column(
        modifier = modifier
    ) {
        for (rowNumbers in winningNumbers.chunked(7)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                rowNumbers.forEach { winningNumber ->
                    WinningNumberLayout(
                        winningNumber = winningNumber
                    )
                }
            }
        }
    }
}

@Composable
private fun WinningNumberLayout(
    modifier: Modifier = Modifier,
    winningNumber: WinningNumber
) {
    Box(
        modifier = modifier
            .size(50.dp)
            .border(
                width = 3.dp,
                color = winningNumber.color,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (winningNumber.number != 0) {
            Text(
                text = winningNumber.number.toString()
            )
        }
    }
}