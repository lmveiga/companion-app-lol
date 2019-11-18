package com.gmail.lucasmveigabr.companionlol.data.model.schema


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ImagePropertiesSchema(
    @SerializedName("full")
    val full: String,
    @SerializedName("h")
    val h: Int, // 48
    @SerializedName("w")
    val w: Int, // 48
    @SerializedName("x")
    val x: Int, // 0
    @SerializedName("y")
    val y: Int // 0
)