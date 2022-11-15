package com.ddenfi.expertcapstone.core.data.source.remote.network

import com.ddenfi.expertcapstone.core.data.source.remote.response.GameDetailResponse
import com.ddenfi.expertcapstone.core.data.source.remote.response.GamesResponse
import com.ddenfi.expertcapstone.core.data.source.remote.response.GamesResultsItem
import dagger.hilt.EntryPoint
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Named

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