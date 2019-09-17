package com.gmail.lucasmveigabr.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Summoner(
    @PrimaryKey var id: Long?,
    var summonnerName: String
)