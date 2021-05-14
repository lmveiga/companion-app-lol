package com.gmail.lucasmveigabr.companionlol.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EnemySummoner(
    val summonerName: String,
    val championName: String,
    val championIconUrl: String,
    val championCardUrl: String,
    val spell1: SummonerSpell?,
    val spell2: SummonerSpell?
) : Parcelable {
    companion object {

        @JvmStatic
        fun unidentified(summonerName: String) = EnemySummoner(
            summonerName, "Unidentified", "",
            "", null, null
        )
    }
}