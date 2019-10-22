package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.model.*
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.CustomReceptor
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.SummonerApi
import com.gmail.lucasmveigabr.companionlol.util.Endpoints
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SummonerRepo @Inject constructor(private val summonerApi: SummonerApi, private val receptor: CustomReceptor) {


    fun verifyIfSummonerExists(summoner: String, region: Region): Result<SummonerResponse>{
        try {
            val response = summonerApi.getSummoner(Endpoints.summonerInfo(region, summoner))
                .execute()
            if (response.isSuccessful) {
                return Result.Success(response.body()!!)
            }
            if (response.code() == 404) {
                return Result.Failure(SummonerNotFoundException())
            }
            return Result.Failure(Exception("RIOT API EXCEPTION"))
        } catch (ex: Exception){
            ex.printStackTrace()
            return Result.Failure(ex)
        }
    }

    fun getSummonerActiveMatch(summonerID: String, region: Region): Result<SummonerMatchStatus>{
        try{
            val response = summonerApi.getCurrentGame(Endpoints.gameInfo(region, summonerID)).execute()
            if (response.isSuccessful){
                return Result.Success(response.body()!!)
            }
            if (response.code() == 404)
                return Result.Failure(SummonerNotInMatchException())
            return Result.Failure(Exception("RIOT API EXCEPTION"))
        } catch (ex: Exception){
            ex.printStackTrace()
            return Result.Failure(ex)
        }
    }




}