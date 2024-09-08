package com.luka.grcki_kino_mozzart.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrawResponse(
    @SerialName("gameId") val gameId: Long, // id igre
    @SerialName("drawId") val drawId: Long, // id izvlacenja
    @SerialName("drawTime") val drawTime: Long, // vreme izvlacenja
//    val status: String,
//    val drawBreak: Long,
//    val visualDraw: Long,
//    val pricePoints: Object,
//    val prizeCategories: List<Object>,
//    val wagerStatistics: Object,
)