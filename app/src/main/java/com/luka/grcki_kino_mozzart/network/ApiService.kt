package com.luka.grcki_kino_mozzart.network

import com.luka.grcki_kino_mozzart.network.model.DrawResponse
import com.luka.grcki_kino_mozzart.network.model.DrawResultsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/draws/v3.0/{gameId}/upcoming/{numberOfDraws}")
    suspend fun getLastXDraws(@Path("gameId") gameId: Long = 1100L, @Path("numberOfDraws") numberOfDraws: Int): Response<List<DrawResponse>>

    @GET("/draws/v3.0/{gameId}/{drawId}")
    suspend fun getDraw(@Path("gameId") gameId: String = "1100", @Path("drawId") drawId: Long): Response<DrawResponse>

    @GET("/draws/v3.0/{gameId}/draw-date/{fromDate}/{toDate}")
    suspend fun getDrawResultsForDateRange(
        @Path("gameId") gameId: String = "1100",
        @Path("fromDate") fromDate: String,
        @Path("toDate") toDate: String
    ): Response<DrawResultsResponse>
}