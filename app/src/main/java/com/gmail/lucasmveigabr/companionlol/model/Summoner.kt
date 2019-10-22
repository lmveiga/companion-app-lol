package com.gmail.lucasmveigabr.companionlol.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gmail.lucasmveigabr.companionlol.db.room.RegionTypeConverter

@Entity @TypeConverters(RegionTypeConverter::class)
data class Summoner(
    @PrimaryKey(autoGenerate = false) var encryptedId: String,
    var summonnerName: String,
    var region: Region
)