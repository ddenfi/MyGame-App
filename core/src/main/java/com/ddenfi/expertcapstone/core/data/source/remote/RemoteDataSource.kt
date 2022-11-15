package com.ddenfi.expertcapstone.core.data.source.remote

import android.util.Log
import com.ddenfi.expertcapstone.core.data.source.remote.network.ApiResponse
import com.ddenfi.expertcapstone.core.data.source.remote.network.ApiService
import com.ddenfi.expertcapstone.core.data.source.remote.response.GamesResultsItem
import com.ddenfi.expertcapstone.core.utils.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllGames(page:Int,pageSize:Int): Flow<ApiResponse<List<GamesResultsItem>>> {
        return flow {
            try {
                val response = apiService.getListGames(API_KEY,page,pageSize)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e))
                Log.e("Remote Data Source", e.toString())
            } catch (e: IOException) {
                emit(ApiResponse.Error(e))
                Log.e("Remote Data Source", "Check Connection")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGameById(gameId: Int): Flow<ApiResponse<GameDetailResponse>> {
        return flow {
            try {
                val response = apiService.getGameById(gameId, API_KEY)
                if (response != null) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: HttpException) {
                emit(ApiResponse.Error(e))
                Log.e("Remote Data Source", e.toString())
            } catch (e: IOException) {
                emit(ApiResponse.Error(e))
                Log.e("Remote Data Source", "Check Connection")
            }
        }.flowOn(Dispatchers.IO)
    }
}