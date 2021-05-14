package com.gmail.lucasmveigabr.companionlol.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Participant(
    val championId: Int,
    val perks: Perks,
    val profileIconId: Int,
    val spell1Id: Int,
    val spell2Id: Int,
    val summonerId: String,
    val summonerName: String,
    val teamId: Int
) : Parcelable