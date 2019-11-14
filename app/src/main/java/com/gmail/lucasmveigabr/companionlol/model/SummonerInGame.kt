package com.gmail.lucasmveigabr.companionlol.model

import com.google.gson.GsonBuilder

data class SummonerInGame(
    val isLoading: Boolean,
    val summoner: Summoner,
    val game: SummonerMatchStatus?
) {

    companion object {
        @JvmStatic
        fun fromJson(json: String): SummonerInGame? {
            try {
                val gson = GsonBuilder().create()
                return gson.fromJson<SummonerInGame>(json, SummonerInGame::class.java)
            } catch (ex: Exception) {
                ex.printStackTrace()
                return null
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is SummonerInGame) return false
        return summoner.encryptedId == other.summoner.encryptedId
    }

    override fun hashCode(): Int {
        return summoner.encryptedId.hashCode()
    }

    fun toJson(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }
}
