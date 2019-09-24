package com.gmail.lucasmveigabr.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.App
import com.gmail.lucasmveigabr.model.Result
import com.gmail.lucasmveigabr.util.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SummonerSignupViewModel: ViewModel() {

    private val addSummonerResult = SingleLiveEvent<Result<Boolean>>()

    fun getSummonerResult(): LiveData<Result<Boolean>> = addSummonerResult

    suspend fun addSummoner(summoner: String){
        GlobalScope.launch {

        }
    }

}