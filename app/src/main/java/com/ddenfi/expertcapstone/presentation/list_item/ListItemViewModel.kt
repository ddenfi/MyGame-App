package com.ddenfi.expertcapstone.presentation.list_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ddenfi.expertcapstone.core.domain.use_case.GameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListItemViewModel @Inject constructor(private val gameUseCase: GameUseCase) : ViewModel() {
    val allGame = gameUseCase.getAllGame()
}