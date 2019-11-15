package com.gmail.lucasmveigabr.companionlol.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EnemySummoner(
    val summonerName: String,
    val championName: String,
    val championIconUrl: String,
    val championCardUrl: String,
    val spell1: SpellSumm?,
    val spell2: SpellSumm?
): Parcelable {
    companion object {

        @JvmStatic
        fun unidentified(summonerName: String) = EnemySummoner(
            summonerName, "Unidentified", "",
            "", null, null)
    }
}