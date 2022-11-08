package com.ddenfi.expertcapstone.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ddenfi.expertcapstone.core.data.source.local.entity.Converters
import com.ddenfi.expertcapstone.core.data.source.local.entity.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class GameDatabase : RoomDatabase() {

    abstract fun gameDao(): GameDao
}