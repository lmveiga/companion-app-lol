package com.gmail.lucasmveigabr.companionlol.model

data class EnemySummoner (
    val summonerName: String,
    val championName: String,
    val championIconUrl: String,
    val championCardUrl: String,
    val spell1: SpellSumm?,
    val spell2: SpellSumm?,
    var firstSpellCd: Double,
    var secondSpellCd: Double
)