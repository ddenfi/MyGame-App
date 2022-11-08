package com.ddenfi.expertcapstone.presentation.detail_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ddenfi.expertcapstone.core.domain.use_case.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailGameViewModel @Inject constructor(private val gameUseCase: GameUseCase) : ViewModel() {

    fun getDetailGame(gameId: Int) = gameUseCase.getDetailGame(gameId).asLiveData()

    fun setFavoriteGame(gameId: Int, isFavorite: Boolean) =
        gameUseCase.setFavoriteGame(gameId, isFavorite)
}