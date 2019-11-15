package com.gmail.lucasmveigabr.companionlol.model

import android.os.Parcelable
import com.google.gson.GsonBuilder
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ActiveGameChampionsAdapterState(
    val enemies: List<EnemySummoner>
): Parcelable