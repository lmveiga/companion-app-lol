package com.gmail.lucasmveigabr.companionlol.model

import android.os.Parcelable
import com.google.gson.GsonBuilder
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SummonerInGame(
    val isLoading: Boolean,
    val summoner: Summoner,
    val game: SummonerMatchStatus?
): Parcelable {

    override fun equals(other: Any?): Boolean {
        if (other !is SummonerInGame) return false
        return summoner.encryptedId == other.summoner.encryptedId
    }

    override fun hashCode(): Int {
        return summoner.encryptedId.hashCode()
    }

}
