package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.model.*
import com.gmail.lucasmveigabr.companionlol.data.api.LeagueApi
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.RuntimeException

class LeagueApiTd: LeagueApi {


    var notFound: Boolean = false
    var otherError: Boolean = false
    var networkError: Boolean = false
    var summonerIdInformed: String = ""
    var hasBeenCalled: Boolean = false


    override fun getSummoner(name: String): Call<SummonerResponse> {
        return object : Call<SummonerResponse> {
            override fun enqueue(callback: Callback<SummonerResponse>) {
                throw RuntimeException()
            }

            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<SummonerResponse> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<SummonerResponse> {
                if (notFound)
                    return Response.error(404, ResponseBody.create(null, "body"))
                if (otherError)
                    return Response.error(500, ResponseBody.create(null, "body"))
                if (networkError){
                    throw IOException("network error")
                }
                return Response.success(
                    SummonerResponse(
                        1, "name", "puuid",
                        1, "1", "id", 0
                    )
                )
            }

            override fun request(): Request {
                throw RuntimeException()
            }
        }
    }

    override fun getCurrentGame(encryptedId: String): Call<SummonerMatchStatus> {
        summonerIdInformed = encryptedId
        return object: Call<SummonerMatchStatus>{
            override fun enqueue(callback: Callback<SummonerMatchStatus>) {
                throw RuntimeException()
            }

            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<SummonerMatchStatus> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<SummonerMatchStatus> {
                if (notFound){
                    return Response.error(404, ResponseBody.create(null, ""))
                }
                if (otherError)
                    return Response.error(500, ResponseBody.create(null, ""))
                if (networkError) throw IOException()
                return Response.success(SummonerMatchStatus(ArrayList<BannedChampion>(),
                    1, 1, "mode", 1, 1, "type",
                    1, Observers("key"), ArrayList<Participant>(), "plat_id"
                ))
            }

            override fun request(): Request {
                throw RuntimeException()
            }

        }
    }

    override fun getChampions(url: String): Call<ChampionData> {
        return object: Call<ChampionData>{
            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<ChampionData> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<ChampionData> {
                hasBeenCalled = true
                if (otherError)
                    return Response.error(500, ResponseBody.create(null, ""))
                if (networkError) throw IOException()
                return Response.success(getChampionData())
            }

            override fun request(): Request {
                throw RuntimeException()
            }

            override fun enqueue(callback: Callback<ChampionData>) {
                throw RuntimeException()
            }

        }
    }

    override fun getSpells(url: String): Call<SpellsResponse> {
        return object: Call<SpellsResponse>{
            override fun enqueue(callback: Callback<SpellsResponse>) {
                throw RuntimeException()
            }

            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<SpellsResponse> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<SpellsResponse> {
                hasBeenCalled = true
                if (otherError)
                    return Response.error(500, ResponseBody.create(null, ""))
                if (networkError) throw IOException()
                return Response.success(getSpellData())
            }

            override fun request(): Request {
                throw RuntimeException()
            }

        }
    }

    private fun getSpellData(): SpellsResponse{
        val spells = HashMap<String, SpellSummSchema>()
        spells["SummonerBarrier"] = SpellSummSchema(ArrayList(), "desc",
            ImageX("SummonerBarrier.png", 0,0,0,0),
            "21", "Barrier")
        spells["SummonerBoost"] = SpellSummSchema(ArrayList(), "desc",
             ImageX("SummonerBoost.png", 0,0,0,0),
            "1", "Cleanse")
        return SpellsResponse(spells, "type", "version")
    }

    private fun getChampionData(): ChampionData{
        val champions = HashMap<String, ChampionSchema>()
        champions["Aatrox"] = ChampionSchema("9.21.1", "Aatrox", 266, "Aatrox", "The Darkin Blade")
        champions ["Aatrox"] = ChampionSchema("9.21.1", "Ahri", 103, "Ahri", "the Nine-Tailed Fox")
        return ChampionData("champion", "standaloneComplex", "9.21.1",
            champions)
    }


}
