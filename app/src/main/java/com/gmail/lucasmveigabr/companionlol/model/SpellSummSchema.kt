package com.gmail.lucasmveigabr.companionlol.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SpellSummSchema(
    @SerializedName("cooldown")
    val cooldown: List<Double>,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: ImageX,
    @SerializedName("key")
    val key: String, // 21
    @SerializedName("name")
    val name: String // Barrier
)