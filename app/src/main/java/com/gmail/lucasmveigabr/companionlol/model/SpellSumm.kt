package com.gmail.lucasmveigabr.companionlol.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SpellSumm(
    @SerializedName("cooldown")
    val cooldown: List<Double>,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: ImageX,
    @SerializedName("key")
    val key: String, // 21
    @SerializedName("name")
    val name: String, // Barrier
    @SerializedName("range")
    val range: List<Int>,
    @SerializedName("rangeBurn")
    val rangeBurn: String, // 1200
    @SerializedName("tooltip")
    val tooltip: String // Temporarily shields {{ f1 }} damage from your champion for 2 seconds.
)