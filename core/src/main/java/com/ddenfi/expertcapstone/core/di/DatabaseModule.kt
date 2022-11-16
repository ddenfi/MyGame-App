package com.ddenfi.expertcapstone.core.di

import android.content.Context
import androidx.room.Room
import com.ddenfi.expertcapstone.core.data.source.local.room.GameDao
import com.ddenfi.expertcapstone.core.data.source.local.room.GameDatabase
import com.ddenfi.expertcapstone.core.data.source.local.room.GameDetailDao
import com.ddenfi.expertcapstone.core.data.source.local.room.RemoteKeysDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    val passphrase: ByteArray = SQLiteDatabase.getBytes("ddenfi".toCharArray())
    val factory = SupportFactory(passphrase)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase = Room.databaseBuilder(
        context,
        GameDatabase::class.java, "game.db"
    ).fallbackToDestructiveMigration()
        .openHelperFactory(factory)
        .build()

    @Provides
    fun provideGameDao(database: GameDatabase): GameDao = database.gameDao()

    @Provides
    fun provideGameDetailDao(database: GameDatabase): GameDetailDao = database.gameDetailDao()

    @Provides
    fun provideRemoteKeysDao(database: GameDatabase): RemoteKeysDao = database.remoteKeysDao()
}