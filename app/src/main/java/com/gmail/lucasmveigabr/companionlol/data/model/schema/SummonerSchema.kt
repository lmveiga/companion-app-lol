package com.gmail.lucasmveigabr.companionlol.data.model.schema

import com.google.gson.annotations.SerializedName

data class SummonerSchema(

    @SerializedName("profileIconId") val profileIconId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("puuid") val puuid: String,
    @SerializedName("summonerLevel") val summonerLevel: Int,
    @SerializedName("accountId") val accountId: String,
    @SerializedName("id") val id: String,
    @SerializedName("revisionDate") val revisionDate: Long
)