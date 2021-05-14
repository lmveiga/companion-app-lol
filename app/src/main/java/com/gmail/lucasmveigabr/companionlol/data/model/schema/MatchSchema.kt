package com.gmail.lucasmveigabr.companionlol.data.model.schema

import android.os.Parcelable
import com.gmail.lucasmveigabr.companionlol.data.model.Participant
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchSchema(
    val participants: List<Participant>
) : Parcelable