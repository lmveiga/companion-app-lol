package com.gmail.lucasmveigabr.companionlol.screens.active_game_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.App
import com.gmail.lucasmveigabr.companionlol.db.room.SummonerDao
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.model.Summoner
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame
import com.gmail.lucasmveigabr.companionlol.networking.repo.SummonerRepo
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActiveGameListViewModel : ViewModel() {

    @Inject
    lateinit var summonerDao: SummonerDao

    @Inject
    lateinit var summonerRepo: SummonerRepo



    private val observers = CompositeDisposable()


    init {
        App.appComponent?.inject(this)
    }

    fun getSummoners() = summonerDao.getSummoners()

    fun getObservableForSummoner(summoner: Summoner): LiveData<SummonerInGame>{
        val result = MutableLiveData<SummonerInGame>()
        observers.add(Observable.create<Unit>{
            when (val response = summonerRepo.getSummonerActiveMatch(summoner.encryptedId, summoner.region)){
                is Result.Success -> {
                    result.postValue(SummonerInGame(false, summoner, response.data))
                }
                is Result.Failure -> {
                    result.postValue(SummonerInGame(false, summoner, null))
                }
            }
        }.subscribeOn(Schedulers.io()).subscribe())
        return result
    }

    override fun onCleared() {
        super.onCleared()
        observers.clear()
    }
}