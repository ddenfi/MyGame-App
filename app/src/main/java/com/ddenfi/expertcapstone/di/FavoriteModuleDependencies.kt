package com.ddenfi.expertcapstone.di

import com.ddenfi.expertcapstone.core.domain.use_case.GameUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun providesGameUseCase(): GameUseCase
}