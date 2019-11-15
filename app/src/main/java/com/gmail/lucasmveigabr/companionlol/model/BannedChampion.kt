package com.gmail.lucasmveigabr.companionlol.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BannedChampion(
    val championId: Int,
    val pickTurn: Int,
    val teamId: Int
): Parcelable