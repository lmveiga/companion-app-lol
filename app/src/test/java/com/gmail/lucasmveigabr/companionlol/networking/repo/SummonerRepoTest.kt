package com.gmail.lucasmveigabr.companionlol.networking.repo

import com.gmail.lucasmveigabr.companionlol.data.model.Region
import com.gmail.lucasmveigabr.companionlol.data.model.Result
import com.gmail.lucasmveigabr.companionlol.data.model.exception.SummonerNotFoundException
import com.gmail.lucasmveigabr.companionlol.data.repository.SummonerRepo
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SummonerRepoTest {

    private lateinit var sut: SummonerRepo
    private lateinit var api: LeagueApiTd


    @Before
    fun setUp() {
        api = LeagueApiTd()
        sut = SummonerRepo(api)
    }

    @Test
    fun `when summoner exists should return successful result`() {
        success()
        val result = sut.verifyIfSummonerExists("summoner", Region.BR)
        assertThat(result, instanceOf(Result.Success::class.java))
        assertThat((result as Result.Success).data.name, `is`("name"))
    }

    @Test
    fun `when http 404 error occurs should return summoner not found exception`() {
        summonerNotFound()
        val result = sut.verifyIfSummonerExists("summoner", Region.BR)
        assertThat(result, instanceOf(Result.Failure::class.java))
        assertThat(
            (result as Result.Failure).error,
            instanceOf(SummonerNotFoundException::class.java)
        )
    }

    @Test
    fun `when other error occurs should fail with correct exception`() {
        otherError()
        val result = sut.verifyIfSummonerExists("summoner", Region.BR)
        assertThat(result, instanceOf(Result.Failure::class.java))
        assertThat(
            (result as Result.Failure).error,
            not(instanceOf(SummonerNotFoundException::class.java))
        )
    }

    @Test
    fun `when network error occurs should return failure correctly`() {
        networkError()
        val result = sut.verifyIfSummonerExists("summoner", Region.BR)
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

    private fun networkError() {
        api.networkError = true
    }


}