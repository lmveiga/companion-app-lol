package com.gmail.lucasmveigabr.companionlol.screen.activegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame

class CurrentGameViewModel : ViewModel() {

    private val _currentGame = MutableLiveData<SummonerInGame>()
    val currentGame: LiveData<SummonerInGame> get() = _currentGame

    fun setCurrentGame(currentGame: SummonerInGame) {
        this._currentGame.value = currentGame
    }

}