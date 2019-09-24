package com.gmail.lucasmveigabr.networking.retrofit

import com.gmail.lucasmveigabr.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class CustomReceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val builder = original.newBuilder()
            .header("X-Riot-Token", BuildConfig.RiotAPIKey)
        val request = builder.build()
        return chain.proceed(request)
    }
}