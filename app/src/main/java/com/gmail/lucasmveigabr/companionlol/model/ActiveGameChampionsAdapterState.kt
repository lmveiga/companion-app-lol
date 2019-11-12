package com.gmail.lucasmveigabr.companionlol.model

import com.google.gson.GsonBuilder

data class ActiveGameChampionsAdapterState(
    val enemies: List<EnemySummoner>
) {

    companion object {

        @JvmStatic
        fun fromJson(json: String): ActiveGameChampionsAdapterState? {
            try {
                val gson = GsonBuilder().create()
                return gson.fromJson(json, ActiveGameChampionsAdapterState::class.java)
            } catch (ex: Exception) {
                ex.printStackTrace()
                return null
            }
        }

    }

    fun toJson(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }
}