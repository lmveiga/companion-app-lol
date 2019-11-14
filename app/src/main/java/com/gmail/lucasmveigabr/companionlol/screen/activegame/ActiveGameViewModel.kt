package com.gmail.lucasmveigabr.companionlol.screen.activegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.app.App
import com.gmail.lucasmveigabr.companionlol.model.*
import com.gmail.lucasmveigabr.companionlol.data.repository.ChampionRepo
import com.gmail.lucasmveigabr.companionlol.data.repository.SpellsRepo
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

    private fun getEnemySpellOrNull(id: Int): SpellSummSchema? {
        when (val result = spellsRepo.getSpell(id)) {
            is Result.Success -> {
                return result.data
            }
            is Result.Failure -> {
                return null
            }
        }
    }

    fun setCurrentGame(game: SummonerInGame, recreation: Boolean = false) {
        if (currentGame.value != null) return
        currentGame.value = game
        if (recreation) return
        val user =
            game.game?.participants?.firstOrNull { it.summonerId == game.summoner.encryptedId }
                ?: throw Exception("ERROR")
        val enemies = game.game.participants.filter { it.teamId != user.teamId }
        Observable.create<Unit> {
            val enemyList = ArrayList<EnemySummoner>()
            for (enemy in enemies) {
                when (val championName = championRepo.getChampion(enemy.championId)) {
                    is Result.Success -> {
                        val spell1 = getEnemySpellOrNull(enemy.spell1Id)
                        val spell2 = getEnemySpellOrNull(enemy.spell2Id)
                        enemyList.add(
                            EnemySummoner(
                                enemy.summonerName, championName.data.name,
                                Endpoints.championIcon(championName.data.id),
                                Endpoints.championCard(championName.data.id),
                                SpellSumm.map(spell1), SpellSumm.map(spell2)
                            )
                        )
                    }
                    is Result.Failure -> {
                        enemyList.add(
                            EnemySummoner(
                                enemy.summonerName, "Unidentified", "",
                                "", null, null
                            )
                        )
                    }
                }
            }
            this.enemies.postValue(enemyList)
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}