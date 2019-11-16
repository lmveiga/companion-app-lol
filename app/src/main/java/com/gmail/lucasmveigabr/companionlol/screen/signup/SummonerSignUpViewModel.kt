package com.gmail.lucasmveigabr.companionlol.screen.signup

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.model.Region
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.model.Summoner
import com.gmail.lucasmveigabr.companionlol.model.SummonerNotFoundException
import com.gmail.lucasmveigabr.companionlol.data.repository.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.util.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SummonerSignUpViewModel @Inject constructor(private val summonerRepo: SummonerRepo, private val summonerDao: SummonerDao): ViewModel() {


    enum class AddSummonerResult { SUCCESS, NOT_FOUND, NETWORK_ERROR }

    private val addSummonerResult = SingleLiveEvent<AddSummonerResult>()

    fun getSummonerResult(): LiveData<AddSummonerResult> = addSummonerResult

    @SuppressLint("CheckResult")
    fun addSummoner(summoner: String, region: Region) {
        Observable.create<Unit> {
            when (val result = summonerRepo.verifyIfSummonerExists(summoner, region)) {
                is Result.Success -> {
                    summonerDao.insertSummoner(
                        Summoner(
                            result.data.id, result.data.name,
                            region
                        )
                    )
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