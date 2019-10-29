package com.gmail.lucasmveigabr.companionlol

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.util.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class NavigationViewModel: ViewModel() {


    private val summonerDao = App.appComponent!!.summonerDao()
    private val navigation = SingleLiveEvent<NavigationEvent>()

    fun getNavigation(): LiveData<NavigationEvent> = navigation

    fun setNavigation(event: NavigationEvent){
        navigation.postValue(event)
    }

    fun noFragmentsAttached() {
        Observable.create<Boolean> {
            if (summonerDao.getSummonerCount() > 0){
                navigation.postValue(NavigationEvent.ActiveGameListNavigation())
            } else {
                navigation.postValue(NavigationEvent.SummonerSignupNavigation(true))
            }
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}