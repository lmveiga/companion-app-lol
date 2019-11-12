package com.gmail.lucasmveigabr.companionlol.networking.repo

import androidx.collection.SparseArrayCompat
import com.gmail.lucasmveigabr.companionlol.model.ChampionSchema
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.LeagueApi
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChampionRepo @Inject constructor(private val leagueApi: LeagueApi) {

    private val cache = SparseArrayCompat<ChampionSchema>()

    @Synchronized
    fun getChampion(id: Int): Result<ChampionSchema> {
        try {
            val cachedValue = cache.get(id)
            if (cachedValue != null) return Result.Success(cachedValue)

            val response = leagueApi.getChampions(Endpoints.champions()).execute()
            response.body()?.let { championData ->
                var result: ChampionSchema? = null
                championData.data.forEach { champion ->
                    cache.put(champion.value.key, champion.value)
                    if (champion.value.key == id) {
                        result = champion.value
                    }
                }
                result?.let {
                    return Result.Success(it)
                }
            }
            return Result.Failure(Exception("Unable to find champion"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Result.Failure(ex)
        }
    }

}