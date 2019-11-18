package com.gmail.lucasmveigabr.companionlol.features.signup

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.data.model.Region
import com.gmail.lucasmveigabr.companionlol.data.model.Result
import com.gmail.lucasmveigabr.companionlol.data.model.Summoner
import com.gmail.lucasmveigabr.companionlol.data.model.exception.SummonerNotFoundException
import com.gmail.lucasmveigabr.companionlol.data.repository.SummonerRepo
import com.gmail.lucasmveigabr.companionlol.util.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SummonerSignUpViewModel @Inject constructor(
    private val summonerRepo: SummonerRepo,
    private val summonerDao: SummonerDao
) : ViewModel() {


    enum class AddSummonerResult { SUCCESS, NOT_FOUND, NETWORK_ERROR }

    private val _addSummonerResult = SingleLiveEvent<AddSummonerResult>()
    val addSummonerResult: LiveData<AddSummonerResult> get() = _addSummonerResult

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
                    _addSummonerResult.postValue(AddSummonerResult.SUCCESS)
                }
                is Result.Failure -> {
                    if (result.error is SummonerNotFoundException) {
                        _addSummonerResult.postValue(AddSummonerResult.NOT_FOUND)
                    } else {
                        _addSummonerResult.postValue(AddSummonerResult.NETWORK_ERROR)
                    }
                }
            }
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}