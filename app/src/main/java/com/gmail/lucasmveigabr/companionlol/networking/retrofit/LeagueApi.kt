package com.gmail.lucasmveigabr.companionlol.networking.retrofit

import com.gmail.lucasmveigabr.companionlol.model.ChampionData
import com.gmail.lucasmveigabr.companionlol.model.SpellsResponse
import com.gmail.lucasmveigabr.companionlol.model.SummonerMatchStatus
import com.gmail.lucasmveigabr.companionlol.model.SummonerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface LeagueApi {

    @GET
    fun getSummoner(@Url url: String): Call<SummonerResponse>

    @GET
    fun getCurrentGame(@Url url: String): Call<SummonerMatchStatus>

    @GET
    fun getChampions(@Url url: String): Call<ChampionData>

    @GET
    fun getSpells(@Url url: String): Call<SpellsResponse>
}