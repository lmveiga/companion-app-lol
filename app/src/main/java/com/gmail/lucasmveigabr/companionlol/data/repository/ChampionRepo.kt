package com.gmail.lucasmveigabr.companionlol.data.repository

import androidx.collection.SparseArrayCompat
import com.gmail.lucasmveigabr.companionlol.data.api.LeagueApi
import com.gmail.lucasmveigabr.companionlol.model.ChampionSchema
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChampionRepo @Inject constructor(private val leagueApi: LeagueApi) {

    private val cache = SparseArrayCompat<ChampionSchema>()

    @Synchronized
    fun getChampion(id: Int): Result<ChampionSchema> {
        try {
            var result = cache.get(id)
            if (result != null) return Result.Success(result)

            val response = leagueApi.getChampions(Endpoints.champions()).execute()
            val data = response.body()

            if (!response.isSuccessful || data == null) {
                return Result.Failure(Exception("Request Error"))
            }

            for (champion in data.data) {
                if (champion.value.key == id) result = champion.value
                cache.put(champion.value.key, champion.value)
            }
            if (result != null) return Result.Success(result)
            return Result.Failure(Exception("Unable to find champion"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Result.Failure(ex)
        }
    }

}