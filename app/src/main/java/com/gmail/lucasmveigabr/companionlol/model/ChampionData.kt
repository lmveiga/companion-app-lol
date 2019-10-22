package com.gmail.lucasmveigabr.companionlol.model

data class ChampionData(
    val type: String,
    val format: String,
    val version: String,
    val data: List<ChampionSchema>
)