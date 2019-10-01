package com.gmail.lucasmveigabr.companionlol

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.companionlol.model.NavigationEvent
import com.gmail.lucasmveigabr.companionlol.util.SingleLiveEvent

class MainActivityViewModel: ViewModel() {

    private val navigation = SingleLiveEvent<NavigationEvent>()

    fun getNavigation(): LiveData<NavigationEvent> = navigation

}