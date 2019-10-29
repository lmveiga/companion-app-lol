package com.gmail.lucasmveigabr.companionlol.model

data class EnemySummoner (
    val summonerName: String,
    val championName: String,
    val championIconUrl: String,
    val championCardUrl: String,
    var timeFirstSummonerLastUsed: Long,
    var timeSecondSummonerLastUsed: Long
)