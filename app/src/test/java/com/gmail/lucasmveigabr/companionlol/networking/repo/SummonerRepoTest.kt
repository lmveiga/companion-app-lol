package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.model.Region
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.model.SummonerNotFoundException
import com.gmail.lucasmveigabr.companionlol.model.SummonerResponse
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.CustomReceptor
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.SummonerApi
import okhttp3.Request
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.reflect.typeOf

@RunWith(MockitoJUnitRunner::class)
class SummonerRepoTest {

    lateinit var SUT: SummonerRepo

    lateinit var api: SummonerApiTd


    @Before
    fun setUp() {
        api = SummonerApiTd()
        SUT = SummonerRepo(api, CustomReceptor())
    }

    @Test
    fun VerifySummonerExists_SuccessfulHttpReturn_ReturnsSuccess() {
        success()
        val result = SUT.verifyIfSummonerExists("summoner", Region.BR)
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.name, `is`("name"))
    }

    @Test
    fun VerifySummonerExists_404HttpReturn_ReturnsSummonerNotFoundException() {
        summonerNotFound()
        val result = SUT.verifyIfSummonerExists("summoner", Region.BR)
        assertThat(result, instanceOf(Result.Failure::class.java))
        assertThat(
            (result as Result.Failure).error,
            instanceOf(SummonerNotFoundException::class.java)
        )
    }

    @Test
    fun VerifySummonerExists_OtherError_ReturnsFailureWithoutNotFOundException() {
        otherError()
        val result = SUT.verifyIfSummonerExists("summoner", Region.BR)
        assertThat(result, instanceOf(Result.Failure::class.java))
        assertThat(
            (result as Result.Failure).error,
            not(instanceOf(SummonerNotFoundException::class.java))
        )
    }

    @Test
    fun VerifySummonerExists_NetworkError_ReturnsFailureWithoutNotFOundException() {
        networkError()
        val result = SUT.verifyIfSummonerExists("summoner", Region.BR)
        assertThat(result, instanceOf(Result.Failure::class.java))
        assertThat(
            (result as Result.Failure).error,
            not(instanceOf(SummonerNotFoundException::class.java))
        )
    }

    private fun success() {
        //nothing
    }

    private fun summonerNotFound() {
        api.notFound = true
    }

    private fun otherError() {
        api.otherError = true
    }

    private fun networkError(){
        api.networkError = true
    }


}