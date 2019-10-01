package com.gmail.lucasmveigabr.companionlol.model

sealed class NavigationEvent {
    class SummonerSignupNavigation(isFirstSummoner: Boolean): NavigationEvent()
    class ActiveGamesNavigation(): NavigationEvent()
}