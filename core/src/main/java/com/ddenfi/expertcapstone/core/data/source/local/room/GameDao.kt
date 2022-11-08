package com.ddenfi.expertcapstone.core.data.source.local.room

import androidx.room.*
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAllGames(): Flow<List<GameEntity>>

    @Query("SELECT * FROM game WHERE id = :gameId ")
    fun getGame(gameId: Int): Flow<GameEntity>

    @Query("SELECT * FROM game WHERE isFavorite = 1")
    fun getFavoriteGames(): Flow<List<GameEntity>>

    @Query("UPDATE game SET isFavorite = :isFavorite WHERE id = :gameId")
    fun updateFavouriteGame(gameId: Int, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: List<GameEntity>)
}