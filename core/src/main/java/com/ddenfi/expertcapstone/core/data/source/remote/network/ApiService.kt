package com.ddenfi.expertcapstone.core.data.source.remote.network

import com.ddenfi.expertcapstone.core.data.source.remote.response.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getListGames(
        @Query("key") apiKey: String,
        @Query("page") page:Int,
        @Query("page_size") pageSize:Int
    ): GamesResponse

    @GET("games/{gameId}")
    suspend fun getGameById(
        @Path("gameId") gameId: Int,
        @Query("key") apiKey: String
    ): GameDetailResponse?
}