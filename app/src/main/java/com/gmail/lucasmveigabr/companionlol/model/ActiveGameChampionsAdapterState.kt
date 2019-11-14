package com.gmail.lucasmveigabr.companionlol.model

import com.google.gson.GsonBuilder

data class ActiveGameChampionsAdapterState(
    val enemies: List<EnemySummoner>
) {

    fun toJson(): String {
        val gson = GsonBuilder().create()
        return gson.toJson(this)
    }

    companion object {

        @JvmStatic
        fun fromJson(json: String): ActiveGameChampionsAdapterState? {
            return try {
                val gson = GsonBuilder().create()
                gson.fromJson(json, ActiveGameChampionsAdapterState::class.java)
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }
        }

    }

}