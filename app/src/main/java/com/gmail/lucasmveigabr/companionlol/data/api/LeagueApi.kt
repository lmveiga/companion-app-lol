package com.gmail.lucasmveigabr.companionlol.data.api

import com.gmail.lucasmveigabr.companionlol.data.model.schema.ChampionListSchema
import com.gmail.lucasmveigabr.companionlol.data.model.schema.SpellListSchema
import com.gmail.lucasmveigabr.companionlol.data.model.schema.MatchSchema
import com.gmail.lucasmveigabr.companionlol.data.model.schema.SummonerSchema
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface LeagueApi {

    @GET
    fun getSummoner(@Url url: String): Call<SummonerSchema>

    @GET
    fun getCurrentGame(@Url url: String): Call<MatchSchema>

    @GET
    fun getChampions(@Url url: String): Call<ChampionListSchema>

    @GET
    fun getSpells(@Url url: String): Call<SpellListSchema>
}