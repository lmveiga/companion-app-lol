package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.model.*
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.SummonerApi
import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.RuntimeException

class SummonerApiTd: SummonerApi {

    var notFound: Boolean = false
    var otherError: Boolean = false
    var networkError: Boolean = false
    var summonerIdInformed: String = ""


    override fun getSummoner(name: String): Call<SummonerResponse> {
        return object : Call<SummonerResponse> {
            override fun enqueue(callback: Callback<SummonerResponse>) {
                throw RuntimeException()
            }

            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<SummonerResponse> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<SummonerResponse> {
                if (notFound)
                    return Response.error(404, ResponseBody.create(null, "body"))
                if (otherError)
                    return Response.error(500, ResponseBody.create(null, "body"))
                if (networkError){
                    throw IOException("network error")
                }
                return Response.success(
                    SummonerResponse(
                        1, "name", "puuid",
                        1, "1", "id", 0
                    )
                )
            }

            override fun request(): Request {
                throw RuntimeException()
            }
        }
    }

    override fun getCurrentGame(encryptedId: String): Call<SummonerMatchStatus> {
        summonerIdInformed = encryptedId
        return object: Call<SummonerMatchStatus>{
            override fun enqueue(callback: Callback<SummonerMatchStatus>) {
                throw RuntimeException()
            }

            override fun isExecuted(): Boolean {
                throw RuntimeException()
            }

            override fun clone(): Call<SummonerMatchStatus> {
                throw RuntimeException()
            }

            override fun isCanceled(): Boolean {
                throw RuntimeException()
            }

            override fun cancel() {
                throw RuntimeException()
            }

            override fun execute(): Response<SummonerMatchStatus> {
                if (notFound){
                    return Response.error(404, ResponseBody.create(null, ""))
                }
                if (otherError)
                    return Response.error(500, ResponseBody.create(null, ""))
                if (networkError) throw IOException()
                return Response.success(SummonerMatchStatus(ArrayList<BannedChampion>(),
                    1, 1, "mode", 1, 1, "type",
                    1, Observers("key"), ArrayList<Participant>(), "plat_id"
                ))
            }

            override fun request(): Request {
                throw RuntimeException()
            }

        }
    }
}
