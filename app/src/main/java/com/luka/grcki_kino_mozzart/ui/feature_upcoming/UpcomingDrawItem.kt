package com.luka.grcki_kino_mozzart.ui.feature_upcoming

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.luka.grcki_kino_mozzart.ui.model.DrawUi
import com.luka.grcki_kino_mozzart.utils.extensions.toDateTimeFormat
import com.luka.grcki_kino_mozzart.utils.extensions.toHoursMinutes
import kotlinx.coroutines.delay

@Composable
fun UpcomingDrawItem(
    modifier: Modifier = Modifier,
    drawItem: DrawUi,
    onDrawItemClick: (drawItem: DrawUi, isExpired: Boolean) -> Unit,
    onDrawTimedOut: () -> Unit
) {
    var isDrawExpired by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onDrawItemClick(drawItem, isDrawExpired)
            }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = drawItem.drawTime.toDateTimeFormat("HH:mm",) // Do this in ViewModel mapper instead of UI
        )
        DrawItemTimer(
            drawTime = drawItem.drawTime,
            onTimedOut = {
                isDrawExpired = true
                onDrawTimedOut()
            }
        )
    }
}

@Composable
private fun DrawItemTimer(
    modifier: Modifier = Modifier,
    drawTime: Long,
    onTimedOut: () -> Unit
) {
    var remainingTimeMillis by remember { mutableLongStateOf(drawTime - System.currentTimeMillis()) }

    // move countdown functionality to ViewModel ?
    LaunchedEffect(key1 = drawTime) {
        while (remainingTimeMillis > 0) {
            remainingTimeMillis = drawTime - System.currentTimeMillis()
            delay(900L)
        }
        remainingTimeMillis = 0 // don't allow to go under 0
        onTimedOut()
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = remainingTimeMillis.toHoursMinutes(),
            color = if (remainingTimeMillis < 11 * 1000L) Color.Red else Color.Black
        )
    }
}