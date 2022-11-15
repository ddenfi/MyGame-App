package com.ddenfi.expertcapstone.core.data.source.local

import androidx.paging.PagingSource
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import com.ddenfi.expertcapstone.core.data.source.local.entity.RemoteKeys
import com.ddenfi.expertcapstone.core.data.source.local.room.GameDao
import com.ddenfi.expertcapstone.core.data.source.local.room.GameDetailDao
import com.ddenfi.expertcapstone.core.data.source.local.room.RemoteKeysDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val gameDao: GameDao,
    private val gameDetailDao: GameDetailDao,
    private val remoteKeysDao: RemoteKeysDao
) {

    //game
    fun getAllGames(): PagingSource<Int, GameEntity> = gameDao.getAllGames()

    fun getGameById(gameId: Int): Flow<GameEntity> = gameDao.getGame(gameId)

    fun getFavoriteGames(): Flow<List<GameEntity>> = gameDao.getFavoriteGames()

    suspend fun setFavoriteGame(gameId: Int, isFavorite: Boolean) {
        gameDao.updateFavouriteGame(gameId, isFavorite)
    }

    suspend fun insertGame(game: List<GameEntity>) = gameDao.insertGame(game)

    suspend fun deleteAllGame() = gameDao.deleteAllGame()

    //detail game  //TODO : Buat method detail game

    // remote key

    suspend fun insertAllRemoteKey(remoteKey: List<RemoteKeys>) = remoteKeysDao.insertAll(remoteKey)

    suspend fun getRemoteKeysId(id: Int): RemoteKeys? = remoteKeysDao.getRemoteKeysId(id)

    suspend fun deleteRemoteKeys() = remoteKeysDao.deleteRemoteKeys()

}