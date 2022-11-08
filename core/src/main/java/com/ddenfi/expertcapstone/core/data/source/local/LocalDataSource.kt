package com.ddenfi.expertcapstone.core.data.source.local

import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import com.ddenfi.expertcapstone.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val gameDao: GameDao) {
    fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    fun getGameById(gameId: Int): Flow<GameEntity> = gameDao.getGame(gameId)

    fun getFavoriteGames(): Flow<List<GameEntity>> = gameDao.getFavoriteGames()

    fun setFavoriteGame(gameId: Int, isFavorite: Boolean) {
        gameDao.updateFavouriteGame(gameId, isFavorite)
    }

    suspend fun insertGame(game: List<GameEntity>) = gameDao.insertGame(game)
}