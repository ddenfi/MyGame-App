package com.ddenfi.expertcapstone.di

import com.ddenfi.expertcapstone.core.domain.use_case.GameInteractor
import com.ddenfi.expertcapstone.core.domain.use_case.GameUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideGameUseCase(gameInteractor: GameInteractor): GameUseCase
}