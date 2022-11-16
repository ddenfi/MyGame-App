package com.ddenfi.expertcapstone.core.domain.repository


import androidx.paging.PagingData
import com.ddenfi.expertcapstone.core.domain.model.Game
import com.ddenfi.expertcapstone.core.domain.model.GameDetail
import com.ddenfi.expertcapstone.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface IGameRepository {
    fun getAllGame(): Flow<PagingData<Game>>

    fun getGameDetailByID(gameId: Int): Flow<Resource<GameDetail>>

    fun setFavoriteGame(gameId: Int, isFavorite: Boolean)

    fun getAllFavoriteGame(): Flow<Resource<List<GameDetail>>>

}