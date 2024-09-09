package com.luka.grcki_kino_mozzart.utils.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier

fun Modifier.clickableWithoutRipple(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit
) = this.then(
    Modifier.clickable(
        interactionSource = interactionSource,
        indication = null,
        onClick = { onClick() }
    )
)