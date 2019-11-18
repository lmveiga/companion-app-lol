package com.gmail.lucasmveigabr.companionlol.core.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.data.db.dao.SummonerDao
import com.gmail.lucasmveigabr.companionlol.data.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.util.SingleLiveEvent
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NavigationViewModel @Inject constructor(private val summonerDao: SummonerDao) : ViewModel() {


    private val _navigation = SingleLiveEvent<NavigationEvent>()
    val navigation: LiveData<NavigationEvent> get() = _navigation


    fun setNavigation(event: NavigationEvent) {
        _navigation.postValue(event)
    }

    fun noFragmentsAttached() {
        Observable.create<Boolean> {
            if (summonerDao.getSummonerCount() > 0) {
                _navigation.postValue(NavigationEvent.ActiveGameListNavigation)
            } else {
                _navigation.postValue(NavigationEvent.SummonerSignUpNavigation)
            }
        }.subscribeOn(Schedulers.io()).subscribe()
    }

}