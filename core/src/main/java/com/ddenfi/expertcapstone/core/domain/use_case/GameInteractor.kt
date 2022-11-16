package com.ddenfi.expertcapstone.core.domain.use_case

import androidx.paging.PagingData
import com.ddenfi.expertcapstone.core.domain.model.Game
import com.ddenfi.expertcapstone.core.domain.model.GameDetail
import com.ddenfi.expertcapstone.core.domain.repository.IGameRepository
import com.ddenfi.expertcapstone.core.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GameInteractor @Inject constructor(private val gameRepository: IGameRepository) :
    GameUseCase {
    override fun getAllGame(): Flow<PagingData<Game>> = gameRepository.getAllGame()

    override fun getDetailGame(gameId: Int): Flow<Resource<GameDetail>> =
        gameRepository.getGameDetailByID(gameId)

    override fun setFavoriteGame(gameId: Int, isFavorite: Boolean) =
        gameRepository.setFavoriteGame(gameId, isFavorite)

    override fun getAllFavoriteGame(): Flow<Resource<List<GameDetail>>> =
        gameRepository.getAllFavoriteGame()
}