package com.gmail.lucasmveigabr.companionlol.networking.repo

import android.util.SparseArray
import androidx.collection.SparseArrayCompat
import com.gmail.lucasmveigabr.companionlol.model.SpellSumm
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.LeagueApi
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpellsRepo @Inject constructor(private val leagueApi: LeagueApi) {

    private val cache = SparseArrayCompat<SpellSumm>()

    @Synchronized
    fun getSpell(id: Int): Result<SpellSumm>{
        try {
            val cachedValue = cache.get(id)
            if (cachedValue != null) return Result.Success(cachedValue)
            val result = leagueApi.getSpells(Endpoints.summonersSpells()).execute()
            result.body()?.let {
                var result: SpellSumm? = null
                for (spell in it.data) {
                    if (spell.value.key == id.toString()) result = spell.value
                    cache.put(spell.value.key.toInt(), spell.value)
                }
                result?.let { return Result.Success(result) }
            }
            return Result.Failure(Exception("Unable to find summoner spell"))
        } catch (ex: Exception){
            ex.printStackTrace()
            return Result.Failure(ex)
        }
    }



}