package com.gmail.lucasmveigabr.companionlol.data.model

import android.os.Parcelable
import com.gmail.lucasmveigabr.companionlol.data.model.schema.SpellSummSchema
import kotlinx.parcelize.Parcelize

@Parcelize
data class SummonerSpell(
    val cooldown: Double,
    val icon: String,
    val name: String,
    var cdUntil: Long
) : Parcelable {
    companion object {
        @JvmStatic
        fun map(spell: SpellSummSchema?, isCdReduced: Boolean) = if (spell == null) null else
            SummonerSpell(
                if (isCdReduced) spell.cooldown[spell.cooldown.size - 1] * 0.95 else spell.cooldown[spell.cooldown.size - 1]
                , spell.image.full, spell.name, 0L
            )
    }
}