package com.gmail.lucasmveigabr.companionlol.db.room

import androidx.room.TypeConverter
import com.gmail.lucasmveigabr.companionlol.model.Region
import java.lang.RuntimeException

class RegionTypeConverter {

    companion object {
        @JvmStatic @TypeConverter fun toRegion(region: Int) =
            when (region) {
                Region.BR.ordinal -> Region.BR
                Region.NA.ordinal -> Region.NA
                Region.EUNE.ordinal -> Region.EUNE
                Region.EUW.ordinal -> Region.EUW
                Region.JP.ordinal -> Region.JP
                Region.KR.ordinal -> Region.KR
                Region.LAN.ordinal -> Region.LAN
                Region.LAS.ordinal -> Region.LAS
                Region.OCE.ordinal -> Region.OCE
                Region.RU.ordinal -> Region.RU
                Region.TR.ordinal -> Region.TR
                else -> throw RuntimeException("INVALID REGION")
            }

        @JvmStatic @TypeConverter fun toInt(region: Region) = region.ordinal
    }

}