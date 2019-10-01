package com.gmail.lucasmveigabr.companionlol.screens.signup

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.App
import com.gmail.lucasmveigabr.companionlol.db.room.SummonerDao
import com.gmail.lucasmveigabr.companionlol.model.Region
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.model.Summoner
import com.gmail.lucasmveigabr.companionlol.model.SummonerNotFoundException
import com.gmail.lucasmveigabr.companionlol.networking.repo.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.util.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SummonerSignupViewModel : ViewModel() {

    init {
        App.appComponent?.inject(this)
    }

    enum class AddSummonerResult { SUCCESS, NOT_FOUND, NETWORK_ERROR }

    private val addSummonerResult = SingleLiveEvent<AddSummonerResult>()

    @Inject
    lateinit var summonerRepo: SummonerRepo
    @Inject
    lateinit var summonersDao: SummonerDao

    fun getSummonerResult(): LiveData<AddSummonerResult> = addSummonerResult

    @SuppressLint("CheckResult")
    fun addSummoner(summoner: String, region: Region) {
        Observable.create<Unit> {
            when (val result = summonerRepo.verifyIfSummonerExists(summoner, region)) {
                is Result.Success -> {
                    summonersDao.insertSummoner(Summoner(null, result.data.name,
                        region, result.data.id))
                    addSummonerResult.postValue(AddSummonerResult.SUCCESS)
                }
                is Result.Failure -> {
                    if (result.error is SummonerNotFoundException) {
                        addSummonerResult.postValue(AddSummonerResult.NOT_FOUND)
                    } else {
                        addSummonerResult.postValue(AddSummonerResult.NETWORK_ERROR)
                    }
                }
            }
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}