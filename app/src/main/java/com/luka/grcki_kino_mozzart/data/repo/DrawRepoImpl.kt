package com.luka.grcki_kino_mozzart.data.repo

import com.luka.grcki_kino_mozzart.di.IODispatcher
import com.luka.grcki_kino_mozzart.network.ApiService
import com.luka.grcki_kino_mozzart.network.NetworkResult
import com.luka.grcki_kino_mozzart.network.model.DrawResponse
import com.luka.grcki_kino_mozzart.network.model.DrawResultsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class DrawRepoImpl(
    private val apiService: ApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : DrawRepo {

    override suspend fun fetchLastXDraws(numberOfDraws: Int): NetworkResult<List<DrawResponse>> = withContext(ioDispatcher) {
        return@withContext handleNetworkRequest {
            apiService.getLastXDraws(numberOfDraws = numberOfDraws)
        }
    }

    override suspend fun fetchDraw(drawId: Long): NetworkResult<DrawResponse> = withContext(ioDispatcher) {
        return@withContext handleNetworkRequest {
            apiService.getDraw(drawId = drawId)
        }
    }

    override suspend fun fetchDrawResultsForDateRange(
        fromDate: String, // required "yyyy-mm-dd" format
        toDate: String // required "yyyy-mm-dd" format
    ): NetworkResult<DrawResultsResponse> = withContext(ioDispatcher) {
        return@withContext handleNetworkRequest {
            apiService.getDrawResultsForDateRange(fromDate = fromDate, toDate = toDate)
        }
    }

    private suspend fun <T : Any> handleNetworkRequest(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        return try {
            val response: Response<T> = apiCall.invoke()

            if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                NetworkResult.Success(response.body()!!)
            } else {
                NetworkResult.Error(
                    code = response.code(),
                    message = response.message(),
                )
            }
        } catch (e: HttpException) {
            NetworkResult.Error(
                code = e.code(),
                message = e.message(),
            )
        } catch (e: IOException) {
            NetworkResult.Exception(e) // if (BuildConfig.IS_DEBUG_MODE) e else Exception("Server unreachable.")
        }
    }
}