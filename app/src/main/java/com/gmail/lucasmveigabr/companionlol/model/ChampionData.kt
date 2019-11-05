package com.gmail.lucasmveigabr.companionlol.model

import com.google.gson.JsonObject

data class ChampionData(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, ChampionSchema>
)