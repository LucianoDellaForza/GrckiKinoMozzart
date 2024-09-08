package com.luka.grcki_kino_mozzart.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrawResultsResponse(
    @SerialName("content") val content: List<DrawResultsInfo>
)

@Serializable
data class DrawResultsInfo(
    @SerialName("gameId") val gameId: Long,
    @SerialName("drawId") val drawId: Long,
    @SerialName("drawTime") val drawTime: Long,
    @SerialName("winningNumbers") val winningNumbers: WinningNumbers
)

@Serializable
data class WinningNumbers(
    @SerialName("list") val numbers: List<Int>
)
