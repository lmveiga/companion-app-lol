package com.gmail.lucasmveigabr.companionlol.data.model.schema


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SpellSummSchema(
    @SerializedName("cooldown")
    val cooldown: List<Double>,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: ImagePropertiesSchema,
    @SerializedName("key")
    val key: String, // 21
    @SerializedName("name")
    val name: String // Barrier
)