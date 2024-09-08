package com.luka.grcki_kino_mozzart.ui.common.dialog

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.luka.grcki_kino_mozzart.R

@Composable
fun ErrorDialog(
    modifier: Modifier = Modifier,
    @StringRes title: Int = R.string.error,
    message: String,
    btnText: String,
    onBtnClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = title),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = message,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = onBtnClick
                ) {
                    Text(text = btnText)
                }
            }
        }

    }
}