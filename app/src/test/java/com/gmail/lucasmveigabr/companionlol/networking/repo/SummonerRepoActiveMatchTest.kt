package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.data.model.Region
import com.gmail.lucasmveigabr.companionlol.data.model.Result
import com.gmail.lucasmveigabr.companionlol.data.model.exception.SummonerNotInMatchException
import com.gmail.lucasmveigabr.companionlol.data.repository.SummonerRepo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SummonerRepoActiveMatchTest {

    private lateinit var sut: SummonerRepo
    private lateinit var api: LeagueApiTd


    @Before
    fun setUp() {
        api = LeagueApiTd()
        sut = SummonerRepo(api)
    }

    @Test
    fun `when getSummonerActiveMatch is called and api returns sucessfully should correcly return success`() {
        success()
        val response = sut.getSummonerActiveMatch("id", Region.BR)
        assertThat(response, instanceOf(Result.Success::class.java))
        assertNotNull((response as Result.Success).data)
    }


    @Test
    fun `when getSummonerActiveMatch is called and there is not an active match should return correct exception`() {
        matchNotFound()
        val response = sut.getSummonerActiveMatch("id", Region.BR)
        assertThat(response, instanceOf(Result.Failure::class.java))
        assertThat(
            (response as Result.Failure).error,
            instanceOf(SummonerNotInMatchException::class.java)
        )
    }

    @Test
    fun `when getSummonerActiveMatch is called and a server error occurs should return failure correctly`() {
        serverError()
        val response = sut.getSummonerActiveMatch("id", Region.BR)
        assertThat(response, instanceOf(Result.Failure::class.java))
        assertThat(
            (response as Result.Failure).error,
            not(instanceOf(SummonerNotInMatchException::class.java))
        )
    }

    @Test
    fun `when getSummonerActiveMatch is called and api returns an network error should correctly return failure`() {
        networkError()
        val response = sut.getSummonerActiveMatch("id", Region.BR)
        assertThat(response, instanceOf(Result.Failure::class.java))
        assertThat(
            (response as Result.Failure).error,
            not(instanceOf(SummonerNotInMatchException::class.java))
        )
    }

    private fun success() {
        //no opt-in
    }

    private fun matchNotFound() {
        api.notFound = true
    }

    private fun serverError() {
        api.otherError = true
    }

    private fun networkError() {
        api.networkError = true
    }

}