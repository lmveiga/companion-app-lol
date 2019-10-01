package com.gmail.lucasmveigabr.companionlol.networking.retrofit

import com.gmail.lucasmveigabr.companionlol.model.SummonerMatchStatus
import com.gmail.lucasmveigabr.companionlol.model.SummonerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SummonerApi {

    @GET("/lol/summoner/v4/summoners/by-name/{name}")
    fun getSummoner(@Path("name") name: String): Call<SummonerResponse>

    @GET("/lol/spectator/v4/active-games/by-summoner/{id}")
    fun getCurrentGame(@Path("id") encryptedId: String): Call<SummonerMatchStatus>
}