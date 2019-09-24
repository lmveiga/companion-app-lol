package com.gmail.lucasmveigabr.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gmail.lucasmveigabr.db.room.RegionTypeConverter

@Entity @TypeConverters(RegionTypeConverter::class)
data class Summoner(
    @PrimaryKey var id: Long?,
    var summonnerName: String,
    var region: Region
)