package com.gmail.lucasmveigabr.db.room

import androidx.room.TypeConverter
import com.gmail.lucasmveigabr.model.Region
import java.lang.RuntimeException

class RegionTypeConverter {

    companion object {
        @JvmStatic @TypeConverter fun toRegion(region: Int) =
            when (region) {
                Region.BR.ordinal -> Region.BR
                Region.CN.ordinal -> Region.CN
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
                Region.SEA.ordinal -> Region.SEA
                else -> throw RuntimeException("INVALID REGION")
            }

        @JvmStatic @TypeConverter fun toInt(region: Region) = region.ordinal
    }

}