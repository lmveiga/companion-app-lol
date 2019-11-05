package com.gmail.lucasmveigabr.companionlol.screens.active_game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.App
import com.gmail.lucasmveigabr.companionlol.model.EnemySummoner
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame
import com.gmail.lucasmveigabr.companionlol.model.SummonerMatchStatus
import com.gmail.lucasmveigabr.companionlol.networking.repo.ChampionRepo
import com.gmail.lucasmveigabr.companionlol.networking.repo.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActiveGameViewModel : ViewModel() {

    @Inject
    lateinit var championRepo: ChampionRepo

    private val currentGame = MutableLiveData<SummonerInGame>()
    private val enemies = MutableLiveData<List<EnemySummoner>>()

    init {
        App.appComponent?.inject(this)
    }

    fun getEnemies(): LiveData<List<EnemySummoner>> = enemies

    fun setCurrentGame(game: SummonerInGame) {
        currentGame.value = game
        val user =
            game.game?.participants?.firstOrNull { it.summonerId == game.summoner.encryptedId }
                ?: throw Exception("ERROR")
        val enemies = game.game.participants.filter { it.teamId != user.teamId }
        Observable.create<Unit> {
            val enemyList = ArrayList<EnemySummoner>()
            for (enemy in enemies){
                when (val championName = championRepo.getChampion(enemy.championId)){
                    is Result.Success -> {
                        enemyList.add(
                            EnemySummoner(enemy.summonerName, championName.data.name,
                                        Endpoints.championIcon(championName.data.id),
                                        Endpoints.championCard(championName.data.id),0, 0)
                        )
                    }
                    is Result.Failure -> {
                        enemyList.add(EnemySummoner(enemy.summonerName, "Unidentified", "",
                            "", 0, 0))
                    }
                }
            }
            this.enemies.postValue(enemyList)
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}