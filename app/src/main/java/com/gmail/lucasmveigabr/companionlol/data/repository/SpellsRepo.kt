package com.gmail.lucasmveigabr.companionlol.data.repository

import androidx.collection.SparseArrayCompat
import com.gmail.lucasmveigabr.companionlol.data.api.LeagueApi
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.model.SpellSummSchema
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpellsRepo @Inject constructor(private val leagueApi: LeagueApi) {

    private val cache = SparseArrayCompat<SpellSummSchema>()

    @Synchronized
    fun getSpell(id: Int): Result<SpellSummSchema> {
        try {
            var result = cache.get(id)
            if (result != null) return Result.Success(result)

            val response = leagueApi.getSpells(Endpoints.summonersSpells()).execute()
            val data = response.body()

            if (!response.isSuccessful || data == null) {
                return Result.Failure(Exception("Request Error"))
            }

            for (spell in data.data) {
                if (spell.value.key == id.toString()) result = spell.value
                cache.put(spell.value.key.toInt(), spell.value)
            }
            if (result != null) return Result.Success(result)

            return Result.Failure(Exception("Unable to find summoner spell"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Result.Failure(ex)
        }
    }


}