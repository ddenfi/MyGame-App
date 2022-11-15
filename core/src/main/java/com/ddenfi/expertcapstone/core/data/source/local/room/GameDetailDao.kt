package com.ddenfi.expertcapstone.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameDetailEntity
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDetailDao {
    @Query("SELECT * FROM game_detail WHERE id = :gameId")
    fun getDetailGameById(gameId:Int): Flow<GameDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = GameDetailEntity::class)
    suspend fun insertDetailGame(data:GameDetailEntity)
}