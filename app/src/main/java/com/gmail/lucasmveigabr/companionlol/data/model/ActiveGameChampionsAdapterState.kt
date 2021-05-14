package com.gmail.lucasmveigabr.companionlol.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActiveGameChampionsAdapterState(
    val enemies: List<EnemySummoner>
) : Parcelable