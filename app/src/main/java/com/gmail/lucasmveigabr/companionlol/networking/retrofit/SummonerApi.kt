package com.gmail.lucasmveigabr.companionlol.networking.retrofit

import com.gmail.lucasmveigabr.companionlol.model.SummonerMatchStatus
import com.gmail.lucasmveigabr.companionlol.model.SummonerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface SummonerApi {

    @GET
    fun getSummoner(@Url url: String): Call<SummonerResponse>

    @GET
    fun getCurrentGame(@Url url: String): Call<SummonerMatchStatus>
}