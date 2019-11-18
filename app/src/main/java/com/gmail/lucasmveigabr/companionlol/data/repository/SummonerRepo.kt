package com.gmail.lucasmveigabr.companionlol.data.repository

import com.gmail.lucasmveigabr.companionlol.data.api.LeagueApi
import com.gmail.lucasmveigabr.companionlol.data.model.*
import com.gmail.lucasmveigabr.companionlol.data.model.exception.SummonerNotFoundException
import com.gmail.lucasmveigabr.companionlol.data.model.exception.SummonerNotInMatchException
import com.gmail.lucasmveigabr.companionlol.data.model.schema.MatchSchema
import com.gmail.lucasmveigabr.companionlol.data.model.schema.SummonerSchema
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SummonerRepo @Inject constructor(private val leagueApi: LeagueApi) {


    fun verifyIfSummonerExists(summoner: String, region: Region): Result<SummonerSchema> {
        try {
            val response = leagueApi.getSummoner(Endpoints.summonerInfo(region, summoner))
                .execute()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            }
            if (response.code() == 404) {
                return Result.Failure(SummonerNotFoundException())
            }
            return Result.Failure(Exception("RIOT API EXCEPTION"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Result.Failure(ex)
        }
    }

    fun getSummonerActiveMatch(summonerID: String, region: Region): Result<MatchSchema> {
        try {
            val response =
                leagueApi.getCurrentGame(Endpoints.gameInfo(region, summonerID)).execute()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            }
            if (response.code() == 404)
                return Result.Failure(SummonerNotInMatchException())
            return Result.Failure(Exception("RIOT API EXCEPTION"))
        } catch (ex: Exception) {
            ex.printStackTrace()
            return Result.Failure(ex)
        }
    }


}