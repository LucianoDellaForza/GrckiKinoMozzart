package com.luka.grcki_kino_mozzart.ui.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.luka.grcki_kino_mozzart.R
import com.luka.grcki_kino_mozzart.utils.extensions.toDateTimeFormat

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun DrawInfoLayoutPreview() {
    DrawInfoLayout(
        modifier = Modifier.padding(8.dp),
        drawId = 0,
        drawTime = System.currentTimeMillis()
    )
}

@Composable
fun DrawInfoLayout(
    modifier: Modifier = Modifier,
    drawId: Long,
    drawTime: Long,
) {
    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.draw_play_info, drawTime.toDateTimeFormat("HH:mm"), drawId)
        )
    }
}
