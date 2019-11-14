package com.gmail.lucasmveigabr.companionlol.model

sealed class NavigationEvent {
    object SummonerSignUpNavigation : NavigationEvent()
    object ActiveGameListNavigation : NavigationEvent()
    object ActiveGameNavigation : NavigationEvent()
}