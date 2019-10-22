package com.gmail.lucasmveigabr.companionlol.networking.retrofit

import com.gmail.lucasmveigabr.companionlol.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class CustomReceptor: Interceptor {

    var region: String = "br1"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
            .header("X-Riot-Token", BuildConfig.RiotAPIKey)
            //.url(original.url().url().toString().replace("region", region))
        val request = builder.build()
        return chain.proceed(request)
    }
}