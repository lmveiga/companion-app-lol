package com.gmail.lucasmveigabr.companionlol.data.api.receptor

import com.gmail.lucasmveigabr.companionlol.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RiotApiReceptor : Interceptor {

    //add header with riot api token

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
            .header("X-Riot-Token", BuildConfig.RiotAPIKey)
        val request = builder.build()
        return chain.proceed(request)
    }
}