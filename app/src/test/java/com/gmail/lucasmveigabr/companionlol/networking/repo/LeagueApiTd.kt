package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.data.api.LeagueApi
import com.gmail.lucasmveigabr.companionlol.data.model.schema.*
import com.gmail.lucasmveigabr.companionlol.testutil.data.GameData
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class LeagueApiTd : LeagueApi {


    var notFound: Boolean = false
    var otherError: Boolean = false
    var networkError: Boolean = false
    private var summonerIdInformed: String = ""
    var hasBeenCalled: Boolean = false


    override fun getSummoner(name: String): Call<SummonerSchema> {
        return object : Call<SummonerSchema> {
            override fun enqueue(callback: Callback<SummonerSchema>) {
                throw RuntimeException()
            }

            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<SummonerSchema> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<SummonerSchema> {
                if (notFound)
                    return Response.error(404, ResponseBody.create(null, "body"))
                if (otherError)
                    return Response.error(500, ResponseBody.create(null, "body"))
                if (networkError) {
                    throw IOException("network error")
                }
                return Response.success(
                    SummonerSchema(
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

    override fun getCurrentGame(encryptedId: String): Call<MatchSchema> {
        summonerIdInformed = encryptedId
        return object : Call<MatchSchema> {
            override fun enqueue(callback: Callback<MatchSchema>) {
                throw RuntimeException()
            }

            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<MatchSchema> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<MatchSchema> {
                if (notFound) {
                    return Response.error(404, ResponseBody.create(null, ""))
                }
                if (otherError)
                    return Response.error(500, ResponseBody.create(null, ""))
                if (networkError) throw IOException()
                return Response.success(
                    MatchSchema(GameData.getParticipants())
                )
            }

            override fun request(): Request {
                throw RuntimeException()
            }

        }
    }

    override fun getChampions(url: String): Call<ChampionListSchema> {
        return object : Call<ChampionListSchema> {
            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<ChampionListSchema> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<ChampionListSchema> {
                hasBeenCalled = true
                if (otherError)
                    return Response.error(500, ResponseBody.create(null, ""))
                if (networkError) throw IOException()
                return Response.success(getChampionData())
            }

            override fun request(): Request {
                throw RuntimeException()
            }

            override fun enqueue(callback: Callback<ChampionListSchema>) {
                throw RuntimeException()
            }

        }
    }

    override fun getSpells(url: String): Call<SpellListSchema> {
        return object : Call<SpellListSchema> {
            override fun enqueue(callback: Callback<SpellListSchema>) {
                throw RuntimeException()
            }

            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<SpellListSchema> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<SpellListSchema> {
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

    private fun getSpellData(): SpellListSchema {
        val spells = HashMap<String, SpellSummSchema>()
        spells["SummonerBarrier"] =
            SpellSummSchema(
                ArrayList(), "desc",
                ImagePropertiesSchema(
                    "SummonerBarrier.png",
                    0,
                    0,
                    0,
                    0
                ),
                "21", "Barrier"
            )
        spells["SummonerBoost"] =
            SpellSummSchema(
                ArrayList(), "desc",
                ImagePropertiesSchema(
                    "SummonerBoost.png",
                    0,
                    0,
                    0,
                    0
                ),
                "1", "Cleanse"
            )
        return SpellListSchema(
            spells,
            "type",
            "version"
        )
    }

    private fun getChampionData(): ChampionListSchema {
        val champions = HashMap<String, ChampionSchema>()
        champions["Aatrox"] = ChampionSchema(
            "9.21.1",
            "Aatrox",
            266,
            "Aatrox",
            "The Darkin Blade"
        )
        champions["Aatrox"] = ChampionSchema(
            "9.21.1",
            "Ahri",
            103,
            "Ahri",
            "the Nine-Tailed Fox"
        )
        return ChampionListSchema(
            "champion", "standaloneComplex", "9.21.1",
            champions
        )
    }


}
