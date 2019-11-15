package com.gmail.lucasmveigabr.companionlol.screen.activegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.app.App
import com.gmail.lucasmveigabr.companionlol.data.repository.ChampionRepo
import com.gmail.lucasmveigabr.companionlol.data.repository.SpellsRepo
import com.gmail.lucasmveigabr.companionlol.model.*
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActiveGameViewModel : ViewModel() {

    @Inject
    lateinit var championRepo: ChampionRepo

    @Inject
    lateinit var spellsRepo: SpellsRepo

    private val disposables = CompositeDisposable()

    private val _enemies = MutableLiveData<List<EnemySummoner>>()
    val enemies: LiveData<List<EnemySummoner>>
        get() = _enemies

    init {
        App.appComponent?.inject(this)
    }


    fun setCurrentGame(game: SummonerInGame) {
        val user =
            game.game?.participants?.firstOrNull { it.summonerId == game.summoner.encryptedId }
                ?: throw Exception("ERROR")
        val enemies = game.game.participants.filter { it.teamId != user.teamId }
        disposables.add(Observable.create<Unit> {
            val enemyList = ArrayList<EnemySummoner>()
            for (enemy in enemies) {
                enemyList.add(getEnemySummoner(enemy))
            }
            _enemies.postValue(enemyList)
        }.subscribeOn(Schedulers.io()).subscribe())
    }

    private fun getEnemySummoner(participant: Participant): EnemySummoner {
        return when (val championName = championRepo.getChampion(participant.championId)) {
            is Result.Success -> {
                val spell1 = getEnemySpellOrNull(participant.spell1Id)
                val spell2 = getEnemySpellOrNull(participant.spell2Id)
                EnemySummoner(
                    participant.summonerName, championName.data.name,
                    Endpoints.championIcon(championName.data.id),
                    Endpoints.championCard(championName.data.id),
                    SpellSumm.map(spell1), SpellSumm.map(spell2)
                )
            }
            is Result.Failure -> {
                EnemySummoner.unidentified(participant.summonerName)
            }
        }
    }

    private fun getEnemySpellOrNull(id: Int): SpellSummSchema? {
        return when (val result = spellsRepo.getSpell(id)) {
            is Result.Success -> {
                result.data
            }
            is Result.Failure -> {
                null
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}