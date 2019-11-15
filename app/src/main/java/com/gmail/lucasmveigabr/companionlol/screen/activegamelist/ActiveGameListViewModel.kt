package com.gmail.lucasmveigabr.companionlol.screen.activegamelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.model.Summoner
import com.gmail.lucasmveigabr.companionlol.model.SummonerInGame
import com.gmail.lucasmveigabr.companionlol.data.repository.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.util.executeAsync
import com.gmail.lucasmveigabr.companionlol.util.replace
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ActiveGameListViewModel @Inject constructor(private val summonerDao: SummonerDao,
                                                  private val summonerRepo: SummonerRepo) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _summoners = MutableLiveData<List<SummonerInGame>>()
    val summoners: LiveData<List<SummonerInGame>> get() = _summoners

    init {
        updateSummoners()
    }

    fun refreshButtonClicked(){
        updateSummoners()
    }

    private fun updateSummoners(){
        executeAsync {
            fetchSummonersGame(summonerDao.getSummoners())
        }
    }

    @Synchronized private fun fetchSummonersGame(summoners: List<Summoner>){
        val data = summoners.map { SummonerInGame(true, it, null) } //LOADING
        _summoners.postValue(data)
        disposables.add(Observable.create<SummonerInGame>{emitter ->
            data.forEach {
                emitter.onNext(fetchActiveMatch(it))
            }
            emitter.onComplete()
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe{result ->
            updateSummonersData(result)
        })
    }

    private fun updateSummonersData(result: SummonerInGame) {
        val resultList = _summoners.value!!.replace(result) {
            it.summoner.encryptedId == result.summoner.encryptedId
        }.sortedBy { it.summoner.summonnerName.toLowerCase(Locale("pt", "BR")) }
        _summoners.value = resultList
    }

    private fun fetchActiveMatch(it: SummonerInGame): SummonerInGame {
        return when (val response =
            summonerRepo.getSummonerActiveMatch(it.summoner.encryptedId, it.summoner.region)) {
            is Result.Success -> {
                SummonerInGame(false, it.summoner, response.data)
            }
            is Result.Failure -> {
                SummonerInGame(false, it.summoner, null)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}