package com.gmail.lucasmveigabr.companionlol.screens.active_game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.App
import com.gmail.lucasmveigabr.companionlol.model.*
import com.gmail.lucasmveigabr.companionlol.networking.repo.ChampionRepo
import com.gmail.lucasmveigabr.companionlol.networking.repo.SpellsRepo
import com.gmail.lucasmveigabr.companionlol.networking.repo.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActiveGameViewModel : ViewModel() {

    @Inject
    lateinit var championRepo: ChampionRepo

    @Inject
    lateinit var spellsRepo: SpellsRepo

    private val currentGame = MutableLiveData<SummonerInGame>()
    private val enemies = MutableLiveData<List<EnemySummoner>>()

    init {
        App.appComponent?.inject(this)
    }

    fun getEnemies(): LiveData<List<EnemySummoner>> = enemies

    private fun getEnemySpellOrNull(id: Int): SpellSumm?{
        when (val result = spellsRepo.getSpell(id)){
            is Result.Success -> {
                return result.data
            }
            is Result.Failure -> {
                return null
            }
        }
    }

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
                        val spell1 = getEnemySpellOrNull(enemy.spell1Id)
                        val spell2 = getEnemySpellOrNull(enemy.spell2Id)
                        enemyList.add(
                            EnemySummoner(enemy.summonerName, championName.data.name,
                                        Endpoints.championIcon(championName.data.id),
                                        Endpoints.championCard(championName.data.id),
                                spell1, spell2,0.0, 0.0)
                        )
                    }
                    is Result.Failure -> {
                        enemyList.add(EnemySummoner(enemy.summonerName, "Unidentified", "",
                            "", null, null, 0.0, 0.0))
                    }
                }
            }
            this.enemies.postValue(enemyList)
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}