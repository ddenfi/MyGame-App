package com.ddenfi.expertcapstone.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM game ORDER BY page ASC")
    fun getAllGames(): PagingSource<Int,GameEntity>

    @Query("SELECT * FROM game WHERE id = :gameId ")
    fun getGame(gameId: Int): Flow<GameEntity>

    @Query("SELECT * FROM game WHERE isFavorite = 1")
    fun getFavoriteGames(): Flow<List<GameEntity>>

    @Query("UPDATE game SET isFavorite = :isFavorite WHERE id = :gameId")
    suspend fun updateFavouriteGame(gameId: Int, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = GameEntity::class)
    suspend fun insertGame(game: List<GameEntity>)

    @Query("DELETE FROM game")
    suspend fun deleteAllGame()
}