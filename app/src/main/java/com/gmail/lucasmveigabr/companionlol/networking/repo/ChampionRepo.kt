package com.gmail.lucasmveigabr.companionlol.networking.repo

import android.util.LongSparseArray
import android.util.SparseArray
import android.util.SparseIntArray
import androidx.collection.SparseArrayCompat
import androidx.core.util.contains
import androidx.core.util.containsKey
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.LeagueApi
import javax.inject.Inject
import javax.inject.Singleton
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import java.lang.Exception
import java.lang.RuntimeException

@Singleton
class ChampionRepo @Inject constructor(private val leagueApi: LeagueApi) {

    private val cache = SparseArrayCompat<String>()

    fun getChampion(id: Int): Result<String> {
        try {
            val cachedValue = cache.get(id)
            if (cachedValue != null) return Result.Success(cachedValue)

            val response = leagueApi.getChampions(Endpoints.champions()).execute()
            response.body()?.let { championData ->
                var result = ""
                championData.data.forEach { champion ->
                    cache.put(champion.key, champion.name)
                    if (champion.key == id) {
                        result = champion.name
                    }
                }
                if (result != "")
                    return Result.Success(result)
            }
            return Result.Failure(Exception("Unable to find champion"))
        } catch (ex: Exception) {
            return Result.Failure(ex)
        }
    }

}