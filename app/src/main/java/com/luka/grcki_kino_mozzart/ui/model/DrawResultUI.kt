package com.luka.grcki_kino_mozzart.ui.model

import androidx.compose.ui.graphics.Color

data class DrawResultUI(
    val drawId: Long,
    val drawTime: Long,
    val winningNumbers: List<WinningNumber>
)

data class WinningNumber(
    val number: Int,
) {
    // TODO: make it color resource
    val color: Color = getNumberColor(number)

    private fun getNumberColor(number: Int): Color {
        /**
         * 1-10: Yellow
         * 11-20: Orange
         * 21-30: Red
         * 31-40: Magenta
         * 41-50: Pink
         * 51-60: LightBlue
         * 61-70: Green
         * 71-80: Purple
         */
        // TODO: fix colors
        return when (number) {
            in 1..10 -> Color.Yellow
            in 11..20 -> Color.LightGray
            in 21..30 -> Color.Red
            in 31..40 -> Color.Magenta
            in 41..50 -> Color.Cyan
            in 51..60 -> Color.Blue
            in 61..70 -> Color.Green
            in 71..80 -> Color.DarkGray
            else -> Color.Transparent // for dummy filling numbers
        }
    }
}


