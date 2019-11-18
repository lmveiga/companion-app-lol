package com.gmail.lucasmveigabr.companionlol.features.activegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.data.model.*
import com.gmail.lucasmveigabr.companionlol.data.model.schema.SpellSummSchema
import com.gmail.lucasmveigabr.companionlol.data.repository.ChampionRepo
import com.gmail.lucasmveigabr.companionlol.data.repository.SpellsRepo
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActiveGameViewModel @Inject constructor(
    private val championRepo: ChampionRepo,
    private val spellsRepo: SpellsRepo
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _enemies = MutableLiveData<List<EnemySummoner>>()
    val enemies: LiveData<List<EnemySummoner>>
        get() = _enemies

    private var isGameSet = false

    fun setCurrentGame(game: SummonerInGame) {
        if (isGameSet) return
        isGameSet = true
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
                val isCdReduced = participant.perks.perkIds.contains(8347)
                EnemySummoner(
                    participant.summonerName, championName.data.name,
                    Endpoints.championIcon(championName.data.id),
                    Endpoints.championCard(championName.data.id),
                    SummonerSpell.map(spell1, isCdReduced), SummonerSpell.map(spell2, isCdReduced)
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