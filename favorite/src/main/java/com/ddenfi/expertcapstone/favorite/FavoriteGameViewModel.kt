package com.ddenfi.expertcapstone.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ddenfi.expertcapstone.core.domain.use_case.GameUseCase

class FavoriteGameViewModel(gameUseCase: GameUseCase) : ViewModel() {
    val result = gameUseCase.getAllFavoriteGame().asLiveData()
}