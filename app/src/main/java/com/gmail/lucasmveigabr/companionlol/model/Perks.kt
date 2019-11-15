package com.gmail.lucasmveigabr.companionlol.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Perks(
    val perkIds: List<Int>,
    val perkStyle: Int,
    val perkSubStyle: Int
): Parcelable