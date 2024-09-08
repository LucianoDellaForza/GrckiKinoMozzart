package com.luka.grcki_kino_mozzart.ui.feature_main.draw_play

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OddsInfoItem(
    modifier: Modifier = Modifier,
    topText: String,
    bottomText: String,
    showDivider: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = topText,
            style = TextStyle(fontSize = 15.sp)
        )
        Spacer(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .fillMaxWidth()
                .height(2.dp)
                .background(if (showDivider) Color.LightGray else Color.Transparent),
        )
        Text(
            text = bottomText,
            style = TextStyle(fontSize = 15.sp)
        )
    }
}