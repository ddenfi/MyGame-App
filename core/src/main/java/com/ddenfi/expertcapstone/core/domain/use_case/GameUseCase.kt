package com.ddenfi.expertcapstone.core.domain.use_case

import com.ddenfi.expertcapstone.core.domain.model.Game
import com.ddenfi.expertcapstone.core.domain.model.GameDetail
import com.ddenfi.expertcapstone.core.utils.Resource
import kotlinx.coroutines.flow.Flow

interface GameUseCase {
    fun getAllGame(): Flow<Resource<List<Game>>>
    fun getDetailGame(gameId: Int): Flow<Resource<GameDetail>>
    fun setFavoriteGame(gameId: Int, isFavorite: Boolean)
    fun getAllFavoriteGame(): Flow<Resource<List<Game>>>
}