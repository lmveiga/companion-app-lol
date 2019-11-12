package com.gmail.lucasmveigabr.companionlol.model

data class SpellSumm(
    val cooldown: Double,
    val icon: String,
    val name: String,
    var cdUntil: Long
) {
    companion object {
        @JvmStatic
        fun map(spell: SpellSummSchema?) = if (spell == null) null else
            SpellSumm(spell.cooldown[spell.cooldown.size - 1], spell.image.full, spell.name, 0L)
    }
}