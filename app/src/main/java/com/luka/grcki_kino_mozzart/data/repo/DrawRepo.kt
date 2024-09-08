package com.luka.grcki_kino_mozzart.data.repo

import com.luka.grcki_kino_mozzart.network.NetworkResult
import com.luka.grcki_kino_mozzart.network.model.DrawResponse
import com.luka.grcki_kino_mozzart.network.model.DrawResultsResponse

interface DrawRepo {
    suspend fun fetchLastXDraws(numberOfDraws: Int): NetworkResult<List<DrawResponse>>
    suspend fun fetchDraw(drawId: Long): NetworkResult<DrawResponse>
    suspend fun fetchDrawResultsForDateRange(fromDate: String, toDate: String): NetworkResult<DrawResultsResponse>
}