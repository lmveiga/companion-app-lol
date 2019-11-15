package com.gmail.lucasmveigabr.companionlol.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SummonerMatchStatus(
    val bannedChampions: List<BannedChampion>,
    val gameId: Long,
    val gameLength: Int,
    val gameMode: String,
    val gameQueueConfigId: Int,
    val gameStartTime: Long,
    val gameType: String,
    val mapId: Int,
    val participants: List<Participant>,
    val platformId: String
): Parcelable