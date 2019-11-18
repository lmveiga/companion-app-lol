package com.gmail.lucasmveigabr.companionlol.di

import com.gmail.lucasmveigabr.companionlol.data.api.receptor.RiotApiReceptor
import com.gmail.lucasmveigabr.companionlol.data.api.LeagueApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun provideSummonerApi(retrofit: Retrofit): LeagueApi {
        return retrofit.create(LeagueApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://br1.api.riotgames.com")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(receptor: RiotApiReceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(receptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideReceptor(): RiotApiReceptor =
        RiotApiReceptor()

}