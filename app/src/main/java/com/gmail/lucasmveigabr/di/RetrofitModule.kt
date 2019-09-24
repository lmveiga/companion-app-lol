package com.gmail.lucasmveigabr.di

import com.gmail.lucasmveigabr.networking.retrofit.CustomReceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl("https://br1.api.riotgames.com")
            .build()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(CustomReceptor())
        return builder.build()
    }

}