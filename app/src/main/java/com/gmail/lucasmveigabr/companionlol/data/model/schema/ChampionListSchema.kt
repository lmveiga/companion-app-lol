package com.gmail.lucasmveigabr.companionlol.data.model.schema


data class ChampionListSchema(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, ChampionSchema>
)