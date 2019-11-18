package com.gmail.lucasmveigabr.companionlol.data.model.schema


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SpellListSchema(
    @SerializedName("data")
    val `data`: Map<String, SpellSummSchema>,
    @SerializedName("type")
    val type: String, // summoner
    @SerializedName("version")
    val version: String // 9.21.1
)