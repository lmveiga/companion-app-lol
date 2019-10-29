package com.gmail.lucasmveigabr.companionlol.di

import com.gmail.lucasmveigabr.companionlol.networking.retrofit.CustomReceptor
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.LeagueApi
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
    fun provideSummonerApi(retrofit: Retrofit): LeagueApi{
        return retrofit.create(LeagueApi::class.java)
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("https://region.api.riotgames.com")
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(receptor: CustomReceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(receptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideReceptor(): CustomReceptor = CustomReceptor()

}