package com.gmail.lucasmveigabr.companionlol

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame

class CurrentGameViewModel : ViewModel() {

    private val currentGame = MutableLiveData<SummonerInGame>()


    fun getCurrentGame(): LiveData<SummonerInGame> = currentGame

    fun setCurrentGame(currentGame: SummonerInGame) {
        this.currentGame.value = currentGame
    }

    override fun onCleared() {
        super.onCleared()
    }

}