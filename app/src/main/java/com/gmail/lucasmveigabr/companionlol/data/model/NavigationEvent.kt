package com.gmail.lucasmveigabr.companionlol.data.model

sealed class NavigationEvent {
    object SummonerSignUpNavigation : NavigationEvent()
    object ActiveGameListNavigation : NavigationEvent()
    class ActiveGameNavigation(val summonerInGame: SummonerInGame) : NavigationEvent()
}