package com.gmail.lucasmveigabr.companionlol.model

data class SummonerInGame(
    val isLoading: Boolean,
    val summoner: Summoner,
    val game: SummonerMatchStatus?
){
    override fun equals(other: Any?): Boolean {
        if (other !is  SummonerInGame) return false
        return summoner.encryptedId == (other as SummonerInGame).summoner.encryptedId
    }

    override fun hashCode(): Int {
        return summoner.encryptedId.hashCode()
    }
}
