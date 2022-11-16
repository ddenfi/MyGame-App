package com.ddenfi.expertcapstone.presentation.list_item

import androidx.lifecycle.ViewModel
import com.ddenfi.expertcapstone.core.domain.use_case.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListItemViewModel @Inject constructor(gameUseCase: GameUseCase) : ViewModel() {
    val allGame = gameUseCase.getAllGame()
}