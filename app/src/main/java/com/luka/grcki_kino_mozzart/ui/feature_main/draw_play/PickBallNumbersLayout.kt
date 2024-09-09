package com.luka.grcki_kino_mozzart.ui.feature_main.draw_play

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.luka.grcki_kino_mozzart.R
import com.luka.grcki_kino_mozzart.ui.feature_main.draw_play.PlayDrawViewModel.Companion.BALL_LIMITS
import com.luka.grcki_kino_mozzart.ui.feature_main.draw_play.model.BallNumber
import com.luka.grcki_kino_mozzart.ui.theme.MozzartBlue
import com.luka.grcki_kino_mozzart.utils.extensions.clickableWithoutRipple

@Composable
fun PickBallNumbersLayout(
    modifier: Modifier,
    ballLimit: Int,
    selectedNumbers: List<BallNumber>,
    onRandomizeBallNumbers: () -> Unit,
    onBallLimitChange: (Int) -> Unit,
    onBallNumberClick: (BallNumber) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                shape = RoundedCornerShape(0.dp),
                onClick = {
                    onRandomizeBallNumbers()
                }
            ) {
                Text(text = stringResource(id = R.string.draw_play_random_pick))
            }
            BallLimitDropDown(
                modifier = Modifier,
                dropdownOptions = BALL_LIMITS,
                selectedBallNumberLimit = ballLimit,
                onSelectDropDownOption = { ballLimit ->
                    onBallLimitChange(ballLimit)
                }
            )
        }


        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(),
//            columns = GridCells.Adaptive(128.dp)
            columns = GridCells.Fixed(10),
        ) {
            itemsIndexed(selectedNumbers) { index, ballNumber ->
                BallNumberLayout(
                    modifier = Modifier
                        .fillMaxSize(),
                    text = ballNumber.number.toString(),
                    isMarked = ballNumber.isSelected,
                    onClicked = {
                        onBallNumberClick(ballNumber)
                    }
                )
            }
        }
    }
}

@Composable
private fun BallNumberLayout(
    modifier: Modifier = Modifier,
    text: String,
    isMarked: Boolean,
    onClicked: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clickableWithoutRipple(interactionSource) {
                onClicked()
            }
            .border(
                width = 2.dp,
                color = if (isMarked) MozzartBlue else Color.Transparent,
                shape = CircleShape
            )
            .padding(vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text
        )
    }
}

@Composable
private fun BallLimitDropDown(
    modifier: Modifier = Modifier,
    dropdownOptions: List<Int>,
    selectedBallNumberLimit: Int,
    onSelectDropDownOption: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(8.dp),
    ) {
        Row(
            modifier = modifier
                .clickable { expanded = true }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.draw_play_selected_limit, selectedBallNumberLimit),
//                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = android.R.drawable.arrow_down_float),
                contentDescription = null,
                tint = Color.LightGray
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            dropdownOptions.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier
                        .width(50.dp)
                        .height(40.dp),
                    onClick = {
                        onSelectDropDownOption(option)
                        expanded = false
                    },
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = option.toString(),
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        }
    }
}