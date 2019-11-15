package com.gmail.lucasmveigabr.companionlol.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gmail.lucasmveigabr.companionlol.data.db.typeconverter.RegionTypeConverter
import kotlinx.android.parcel.Parcelize

@Entity
@TypeConverters(RegionTypeConverter::class)
@Parcelize
data class Summoner(
    @PrimaryKey(autoGenerate = false) var encryptedId: String,
    var summonnerName: String,
    var region: Region
): Parcelable