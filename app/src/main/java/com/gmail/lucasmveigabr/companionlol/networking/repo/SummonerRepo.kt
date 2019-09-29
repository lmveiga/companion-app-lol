package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.model.Region
import com.gmail.lucasmveigabr.companionlol.model.Region.*
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.model.SummonerNotFoundException
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.CustomReceptor
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.SummonerApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SummonerRepo @Inject constructor(private val summonerApi: SummonerApi, private val receptor: CustomReceptor) {

    private fun getPrefixForRegion(region: Region) =
        when (region){
            BR -> "br1"
            NA -> "na1"
            EUW -> "euw1"
            EUNE -> "eun1"
            LAN -> "la1"
            LAS -> "la2"
            OCE -> "oc1"
            RU -> "ru"
            TR -> "tr1"
            JP -> "jp1"
            KR -> "kr"
        }

    fun verifyIfSummonerExists(summoner: String, region: Region): Result<String>{
        receptor.region = getPrefixForRegion(region)
        try {
            val response = summonerApi.getSummoner(summoner)
                .execute()
            if (response.isSuccessful) {
                return Result.Success(response.body()?.name!!)
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


}