package com.gmail.lucasmveigabr.companionlol.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Participant(
    val bot: Boolean,
    val championId: Int,
    val perks: Perks,
    val profileIconId: Int,
    val spell1Id: Int,
    val spell2Id: Int,
    val summonerId: String,
    val summonerName: String,
    val teamId: Int
): Parcelable