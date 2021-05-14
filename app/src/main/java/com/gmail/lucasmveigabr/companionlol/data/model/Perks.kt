package com.gmail.lucasmveigabr.companionlol.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Perks(
    val perkIds: List<Int>,
    val perkStyle: Int,
    val perkSubStyle: Int
) : Parcelable