package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.model.Region
import com.gmail.lucasmveigabr.companionlol.model.Result
import com.gmail.lucasmveigabr.companionlol.model.SummonerNotInMatchException
import com.gmail.lucasmveigabr.companionlol.networking.retrofit.CustomReceptor
import org.hamcrest.CoreMatchers.*
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SummonerRepoActiveMatchTest {

    lateinit var SUT: SummonerRepo
    lateinit var api: SummonerApiTd


    @Before
    fun setUp() {
        api = SummonerApiTd()
        SUT = SummonerRepo(api, CustomReceptor())
    }

    @Test
    fun getSummonerActiveMatch_CorrectParametersPassedToApi() {
        SUT.getSummonerActiveMatch("id", Region.BR)
        assertThat(api.summonerIdInformed, `is`("id"))
    }

    @Test
    fun getSummonerActiveMatch_SuccessfulReturn_ReturnsSummonerMatchCorrectly(){
        success();
        val response = SUT.getSummonerActiveMatch("id", Region.BR)
        assertThat(response, instanceOf(Result.Success::class.java))
        assertNotNull((response as Result.Success).data)
    }


    @Test
    fun getSummonerActiveMatch_MatchNotFound_ReturnsSummonerNotInMatchException(){
        matchNotFound()
        val response = SUT.getSummonerActiveMatch("id", Region.BR)
        assertThat(response, instanceOf(Result.Failure::class.java))
        assertThat((response as Result.Failure).error, instanceOf(SummonerNotInMatchException::class.java))
    }

    @Test
    fun getSummonerActiveMatch_ServerError_ReturnsFailureAndCorrectException(){
        serverError()
        val response = SUT.getSummonerActiveMatch("id", Region.BR)
        assertThat(response, instanceOf(Result.Failure::class.java))
        assertThat((response as Result.Failure).error, not(instanceOf(SummonerNotInMatchException::class.java)))
    }

    @Test
    fun getSummonerActiveMatch_NetworkError_ReturnsFailureAndCorrectException(){
        networkError()
        val response = SUT.getSummonerActiveMatch("id", Region.BR)
        assertThat(response, instanceOf(Result.Failure::class.java))
        assertThat((response as Result.Failure).error, not(instanceOf(SummonerNotInMatchException::class.java)))
    }

    private fun success() {
        //no opt-in
    }

    private fun matchNotFound(){
        api.notFound = true
    }

    private fun serverError(){
        api.otherError = true
    }

    private fun networkError(){
        api.networkError = true
    }

}