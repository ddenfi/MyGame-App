package com.ddenfi.expertcapstone.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ddenfi.expertcapstone.core.data.source.local.entity.Converters
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameDetailEntity
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity
import com.ddenfi.expertcapstone.core.data.source.local.entity.RemoteKeys

@Database(
    entities = [GameEntity::class,GameDetailEntity::class,RemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
    abstract fun gameDetailDao(): GameDetailDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}